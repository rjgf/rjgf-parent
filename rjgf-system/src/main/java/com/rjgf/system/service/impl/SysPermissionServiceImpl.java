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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.auth.util.LoginUtil;
import com.rjgf.common.common.exception.BusinessException;
import com.rjgf.common.common.exception.DaoException;
import com.rjgf.common.enums.MenuLevelEnum;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.constant.SystemConstant;
import com.rjgf.system.convert.SysPermissionConvert;
import com.rjgf.system.entity.SysPermission;
import com.rjgf.system.mapper.SysPermissionMapper;
import com.rjgf.system.service.ISysPermissionService;
import com.rjgf.system.service.ISysRolePermissionService;
import com.rjgf.system.vo.req.SysPermissionQueryParam;
import com.rjgf.system.vo.resp.SysPermissionQueryVo;
import com.rjgf.system.vo.resp.SysPermissionTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <pre>
 * 系统权限 服务实现类
 * </pre>
 *
 * @author xula
 * @since 2019-10-25
 */
@Slf4j
@Service
public class SysPermissionServiceImpl extends CommonServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysPermission(SysPermission sysPermission) throws Exception {
        int level = 1;
        String parentIds = sysPermission.getParentIds();
        if (StringUtils.isNotEmpty(parentIds)) {
            String[] arr = parentIds.split(",");
            level = arr.length + 1;
        }
        sysPermission.setLevel(level);
        return super.save(sysPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysPermission(SysPermission sysPermission) throws Exception {
        // 获取权限
        SysPermission updateSysPermission = getById(sysPermission.getId());
        if (updateSysPermission == null) {
            throw new BusinessException("权限不存在");
        }
        // 指定需改的字段
        String parentIds = sysPermission.getParentIds();
        int level = 1;
        if (StringUtils.isNotEmpty(parentIds)) {
            String[] arr = parentIds.split(",");
            level = arr.length + 1;
        }
        updateSysPermission.setParentId(sysPermission.getParentId())
                .setParentIds(sysPermission.getParentIds())
                .setLevel(level)
                .setName(sysPermission.getName())
                .setCode(sysPermission.getCode())
                .setIcon(sysPermission.getIcon())
                .setSort(sysPermission.getSort())
                .setType(sysPermission.getType())
                .setPath(sysPermission.getPath())
                .setRemark(sysPermission.getRemark());

        return super.updateById(updateSysPermission);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysPermission(Long id) throws Exception {
        boolean isExists = sysRolePermissionService.isExistsByPermissionId(id);
        if (isExists) {
            throw new BusinessException("该权限存在角色关联关系，不能删除");
        }
        return super.removeById(id);
    }

    @Override
    public SysPermissionQueryVo getSysPermissionById(Serializable id) throws Exception {
        SysPermission sysPermission = sysPermissionMapper.selectById(id);
        return SysPermissionConvert.INSTANCE.sysPermissionToQueryVo(sysPermission);
    }

    @Override
    public IPage<SysPermissionQueryVo> getSysPermissionPage(SysPermissionQueryParam sysPermissionQueryParam) throws Exception {
        return sysPermissionMapper.getSysPermissionPageList(sysPermissionQueryParam.getPage(), sysPermissionQueryParam);
    }

    @Override
    public boolean isExistsByPermissionIds(List<Long> permissionIds) {
        LambdaQueryWrapper queryWrapper = Wrappers.<SysPermission>lambdaQuery().in(SysPermission::getId,permissionIds);
        return sysPermissionMapper.selectCount(queryWrapper).intValue() == permissionIds.size();
    }

    @Override
    public List<SysPermission> getAllMenuList() throws Exception {
        SysPermission sysPermission = new SysPermission();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(sysPermission).orderByAsc(SysPermission::getLevel,SysPermission::getSort);
        // 获取所有已启用的权限列表
        return sysPermissionMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<SysPermissionTreeVo> getAllMenuTree() throws Exception {
        List<SysPermission> list = getAllMenuList();
        // 转换成树形菜单
        List<SysPermissionTreeVo> treeVos = convertSysPermissionTreeVoList(list);
        return treeVos;
    }

    @Override
    public List<SysPermissionTreeVo> convertSysPermissionTreeVoList(List<SysPermission> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("SysPermission列表不能为空");
        }
        // 按level分组获取map
        Map<Integer, List<SysPermission>> map = list.stream().collect(Collectors.groupingBy(SysPermission::getLevel));
        List<SysPermissionTreeVo> treeVos = new ArrayList<>();
        // 循环获取三级菜单树形集合
        for (SysPermission one : map.get(MenuLevelEnum.ONE.getCode())) {
            SysPermissionTreeVo oneVo = SysPermissionConvert.INSTANCE.permissionToTreeVo(one);
            Long oneParentId = oneVo.getParentId();
            if (oneParentId == null || oneParentId == 0) {
                if (oneVo.getLevel() == MenuLevelEnum.ONE.getCode()) {
                    oneVo.setPath("/" + oneVo.getPath());
                }
                treeVos.add(oneVo);
            }
            List<SysPermission> twoList = map.get(MenuLevelEnum.TWO.getCode());
            if (CollectionUtils.isNotEmpty(twoList)) {
                for (SysPermission two : twoList) {
                    SysPermissionTreeVo twoVo = SysPermissionConvert.INSTANCE.permissionToTreeVo(two);
                    List<SysPermission> threeList = map.get(MenuLevelEnum.THREE.getCode());
                    if (CollectionUtils.isNotEmpty(threeList)) {
                        for (SysPermission three : threeList) {
                            SysPermissionTreeVo threeVo = SysPermissionConvert.INSTANCE.permissionToTreeVo(three);
                            if (threeVo.getParentId().equals(two.getId())) {
                                twoVo.getChildren().add(threeVo);
                            }
                        }
                    }
                    if (two.getParentId().equals(one.getId())) {
                        oneVo.getChildren().add(twoVo);
                    }
                }
            }
        }
        return treeVos;
    }

    @Override
    public List<String> getPermissionCodesByUserId(Long userId) throws Exception {
        return sysPermissionMapper.getPermissionCodesByUserId(userId);
    }

    @Override
    public List<SysPermission> getMenuListByUserId(Long userId) throws Exception {
        return sysPermissionMapper.getMenuListByUserId(userId);
    }

    @Override
    public List<SysPermissionTreeVo> getMenuTreeByUserId(Long userId) throws Exception {
        Set<String> roleCodes = LoginUtil.getRoleCodes();
        List<SysPermission> list;
        // 如果是系统用户获取所有的菜单
        if (roleCodes.contains(SystemConstant.SYSTEM_CODE)) {
            list = getAllMenuList();
        } else {
            list = getMenuListByUserId(userId);
        }
        // 转换成树形菜单
        List<SysPermissionTreeVo> treeVos = convertSysPermissionTreeVoList(list);
        return treeVos;
    }

    @Override
    public void changeMenuState(Long id,Integer state) {
        Integer oldState = StateEnum.checkState(state);
        LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.<SysPermission>lambdaUpdate().set(SysPermission::getState,state).
                eq(SysPermission::getId,id).eq(SysPermission::getState,oldState);
        boolean result = this.update(lambdaUpdateWrapper);
        if (!result) {
            throw new DaoException("数据操作异常！");
        }
    }
}
