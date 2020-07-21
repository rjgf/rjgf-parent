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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rjgf.common.common.exception.BusinessException;
import com.rjgf.common.common.exception.DaoException;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.convert.SysRoleConvert;
import com.rjgf.system.entity.SysPermission;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.mapper.SysRoleMapper;
import com.rjgf.system.service.ISysPermissionService;
import com.rjgf.system.service.ISysRolePermissionService;
import com.rjgf.system.service.ISysRoleService;
import com.rjgf.system.service.ISysUserService;
import com.rjgf.system.vo.req.SysRoleQueryParam;
import com.rjgf.system.vo.req.sysrole.AddSysRoleParam;
import com.rjgf.system.vo.req.sysrole.SysRolePermissionParam;
import com.rjgf.system.vo.req.sysrole.UpdateSysRoleParam;
import com.rjgf.system.vo.resp.SysPermissionTreeVo;
import com.rjgf.system.vo.resp.SysRolePermissionVo;
import com.rjgf.system.vo.resp.SysRoleQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rjgf.common.enums.StateEnum.checkState;


/**
 * <pre>
 * 系统角色 服务实现类
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends CommonServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Autowired
    private ISysUserService sysUserService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRole(AddSysRoleParam addSysRoleParam) throws Exception {
        // 判断角色code是否可用
        String roleCode = addSysRoleParam.getCode();
        boolean isEnable = isExistsByCode(roleCode);
        if (isEnable) {
            throw new BusinessException("角色code已存在");
        }
        SysRole sysRole = SysRoleConvert.INSTANCE.addSysRoleParamToSysRole(addSysRoleParam);
        return super.save(sysRole); }

    @Override
    public boolean updateSysRole(UpdateSysRoleParam updateSysRoleParam) throws Exception {
        Long roleId = updateSysRoleParam.getId();
        SysRole sysRole = getById(roleId);
        if (sysRole == null) {
            throw new BusinessException("该角色不存在");
        }
        // 修改角色
        sysRole.setName(updateSysRoleParam.getName())
                .setType(updateSysRoleParam.getType())
                .setState(updateSysRoleParam.getState())
                .setRemark(updateSysRoleParam.getRemark());
        boolean updateResult = updateById(sysRole);
        if (!updateResult) {
            throw new DaoException("修改系统角色失败");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysRole(Long id) throws Exception {
        // 判断该角色下是否有可用用户，如果有，则不能删除
        boolean isExistsUser = sysUserService.isExistsSysUserByRoleId(id);
        if (isExistsUser) {
            throw new DaoException("该角色下还存在可用用户，不能删除");
        }
        // 角色真实删除
        boolean deleteRoleResult = removeById(id);
        if (!deleteRoleResult) {
            throw new DaoException("删除角色失败");
        }
        // 判断角色是否有权限
        List<Long> sysRolePermissionIds= sysRolePermissionService.getPermissionIdsByRoleId(id);
        // 如果角色没有权限，不需要进行角色权限删除
        if (CollectionUtils.isEmpty(sysRolePermissionIds)) {
            return true;
        }
        // 角色权限关系真实删除
        boolean deletePermissionResult = sysRolePermissionService.deleteSysRolePermissionByRoleId(id);
        if (!deletePermissionResult) {
            throw new DaoException("删除角色权限关系失败");
        }
        return true;
    }

    @Override
    public SysRoleQueryVo getSysRoleById(Serializable id) throws Exception {
        return sysRoleMapper.getSysRoleById(id);
    }

    @Override
    public IPage<SysRoleQueryVo> getSysRolePage(SysRoleQueryParam sysRoleQueryParam) throws Exception {
        return sysRoleMapper.getSysRolePageList(sysRoleQueryParam.getPage(), sysRoleQueryParam);
    }

    @Override
    public boolean isEnableSysRole(Long id) throws Exception {
        SysRole sysRole = new SysRole()
                .setId(id)
                .setState(StateEnum.ENABLE.getCode());
        int count = sysRoleMapper.selectCount(new QueryWrapper<>(sysRole));
        return count > 0;
    }

    @Override
    public boolean isEnableSysRole(List<Long> ids) throws Exception {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getState,StateEnum.ENABLE.getCode()).in(SysRole::getId,ids.toArray());
        int count = sysRoleMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public boolean isExistsByCode(String code) throws Exception {
        SysRole sysRole = new SysRole().setCode(code);
        return sysRoleMapper.selectCount(new QueryWrapper<>(sysRole)) > 0;
    }

    @Override
    public List<SysRole> getSysRoleList() {
        SysRole sysRole = new SysRole().setState(StateEnum.ENABLE.getCode());
        return  sysRoleMapper.selectList(new QueryWrapper<>(sysRole));
    }


    @Override
    public void changeRoleState(Long id, Integer state) {
        int oldState = checkState(state);
        LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.<SysRole>lambdaUpdate().set(SysRole::getState,state)
                .eq(SysRole::getId,id).eq(SysRole::getState,oldState);
        boolean result = this.update(lambdaUpdateWrapper);
        if (!result) {
            throw new DaoException("数据操作异常！");
        }
    }


    @Override
    public SysRolePermissionVo getRoleRolePermission(Long roleId) throws Exception {
        SysRolePermissionVo sysRolePermissionVo = new SysRolePermissionVo();
        List<Long> rolePermissionIds = sysRolePermissionService.getPermissionIdsByRoleId(roleId);
        List<SysPermissionTreeVo> sysPermissionTreeVoList = sysPermissionService.getAllMenuTree();
        sysRolePermissionVo.setRoleId(roleId);
        sysRolePermissionVo.setRolePermissionIds(rolePermissionIds);
        sysRolePermissionVo.setSysPermissionTreeVoList(sysPermissionTreeVoList);
        return sysRolePermissionVo;
    }
}
