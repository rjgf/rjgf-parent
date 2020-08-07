/*
 * Copyright 2019-2029 xula(https://github.com/xula)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rjgf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rjgf.common.common.exception.BusinessException;
import com.rjgf.common.common.exception.DaoException;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysRolePermission;
import com.rjgf.system.mapper.SysRolePermissionMapper;
import com.rjgf.system.service.ISysPermissionService;
import com.rjgf.system.service.ISysRolePermissionService;
import com.rjgf.system.service.ISysRoleService;
import com.rjgf.system.vo.req.sysrole.SysRolePermissionParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <pre>
 * 角色权限关系 服务实现类
 * </pre>
 *
 * @author xula
 * @since 2019-10-25
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl extends CommonServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private ISysRolePermissionService sysRolePermissionService;
    @Autowired
    private ISysPermissionService iSysPermissionService;
    @Autowired
    private ISysRoleService iSysRoleService;

    @Override
    public boolean updateSysRolePermission(SysRolePermissionParam sysRolePermissionParam) throws Exception {
        Long roleId = sysRolePermissionParam.getRoleId();
        // 校验角色是否存在
        SysRole sysRole = iSysRoleService.getById(roleId);
        if (sysRole == null) {
            throw new BusinessException("该角色不存在");
        }
        List<Long> permissionIds = sysRolePermissionParam.getPermissionIds();
        // 校验权限列表是否存在
        if (!iSysPermissionService.isExistsByPermissionIds(permissionIds)) {
            throw new BusinessException("权限列表id匹配失败");
        }
        // 获取之前的权限id集合
        List<Long> beforeList = sysRolePermissionService.getPermissionIdsByRoleId(roleId);
        // 差集计算
        // before：1,2,3,4,5,6
        // after： 1,2,3,4,7,8
        // 删除5,6 新增7,8
        // 此处真实删除，去掉deleted字段的@TableLogic注解
        Set<Long> beforeSet = new HashSet<>(beforeList);
        Set<Long> afterSet = new HashSet<>(permissionIds);
        SetUtils.SetView deleteSet = SetUtils.difference(beforeSet, afterSet);
        SetUtils.SetView addSet = SetUtils.difference(afterSet, beforeSet);
        log.debug("deleteSet = " + deleteSet);
        log.debug("addSet = " + addSet);

        // 删除权限关联
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("role_id",roleId);
        updateWrapper.in("permission_id",deleteSet);
        if (!deleteSet.isEmpty()) {
            boolean deleteResult = sysRolePermissionService.remove(updateWrapper);
            if (!deleteResult) {
                throw new DaoException("删除角色权限关系失败");
            }
        }
        if (CollectionUtils.isEmpty(addSet)) {
            return true;
        }
        // 新增权限关联
        boolean addResult = sysRolePermissionService.saveSysRolePermissionBatch(roleId, addSet);
        if (!addResult) {
            throw new DaoException("新增角色权限关系失败");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRolePermission(Long roleId, List<Long> permissionIds) throws Exception {
        List<SysRolePermission> list = new ArrayList<>();
        permissionIds.forEach(permissionId -> {
            SysRolePermission sysRolePermission = new SysRolePermission()
                    .setRoleId(roleId)
                    .setPermissionId(permissionId)
                    .setState(StateEnum.ENABLE.getCode());
            list.add(sysRolePermission);
        });
        // 批量保存角色权限中间表
        return saveBatch(list, 20);
    }



    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) throws Exception {
        SysRolePermission sysRolePermission = new SysRolePermission()
                .setRoleId(roleId)
                .setState(StateEnum.ENABLE.getCode());
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(sysRolePermission).select(SysRolePermission::getPermissionId);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }

    @Override
    public boolean saveSysRolePermissionBatch(Long roleId, SetUtils.SetView addSet) {
        List<SysRolePermission> list = new ArrayList<>();
        addSet.forEach(id -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            Long permissionId = (Long) id;
            sysRolePermission
                    .setRoleId(roleId)
                    .setPermissionId(permissionId)
                    .setState(StateEnum.ENABLE.getCode());
            list.add(sysRolePermission);
        });
        return saveBatch(list, 20);
    }

    @Override
    public boolean deleteSysRolePermissionByRoleId(Long roleId) throws Exception {
        SysRolePermission sysRolePermission = new SysRolePermission()
                .setRoleId(roleId);
        return remove(new QueryWrapper<>(sysRolePermission));
    }

    @Override
    public Set<String> getPermissionCodesByRoleId(Long roleId) throws Exception {
        return sysRolePermissionMapper.getPermissionCodesByRoleId(roleId);
    }


    @Override
    public Set<String> getPermissionCodesByRoleIds(List<Long> roleIds) throws Exception {
        return sysRolePermissionMapper.getPermissionCodesByRoleIds(roleIds);
    }

    @Override
    public boolean isExistsByPermissionId(Long permissionId) throws Exception {
        // 判断角色权限表是否有关联存在，如果存在，则不能删除
        SysRolePermission sysRolePermission = new SysRolePermission()
                .setPermissionId(permissionId);
        return count(new QueryWrapper(sysRolePermission)) > 0;
    }

}
