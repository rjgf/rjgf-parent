package com.rjgf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rjgf.common.common.exception.BusinessException;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.entity.SysUserRole;
import com.rjgf.system.mapper.SysUserRoleMapper;
import com.rjgf.system.service.ISysUserRoleService;
import com.rjgf.system.service.ISysUserService;
import com.rjgf.system.vo.resp.SysUsersRoleVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xula
 * @since 2020-01-31
 */
@Service
public class SysUserRoleServiceImpl extends CommonServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private ISysUserService iSysUserService;



    @Override
    public List<SysRole> getSysUserRoleList(Long userId) {
        return this.baseMapper.getSysRoleList(userId);
    }

    @Override
    public boolean addUserRole(Long userId, List<Long> roleIds) {
        List<SysUserRole> sysUserRoleList = roleIds.stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            return sysUserRole;
        }).collect(Collectors.toList());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        baseMapper.delete(queryWrapper);
        return saveOrUpdateBatch(sysUserRoleList);
    }

    @Override
    public List<SysUsersRoleVo> getSysUserListByRole(Long roleId) {
        List<SysUser> sysUsers = iSysUserService.list(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getState, StateEnum.ENABLE.getCode()));
        List<SysUserRole> sysUserRoleList = this.baseMapper.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId,roleId));
        if (CollectionUtils.isEmpty(sysUserRoleList)) {
            log.error(String.format("该角色[%s]下不存在用户列表",roleId));
            throw new BusinessException(String.format("该角色[%s]下不存在用户列表",roleId));
        }
        Set<Long> userIds = sysUserRoleList.stream().map(SysUserRole::getUserId).collect(Collectors.toSet());
        List<SysUsersRoleVo> sysUsersRoleVos = new ArrayList<>(sysUsers.size());
        for (SysUser sysUser:sysUsers) {
            SysUsersRoleVo sysUsersRoleVo = new SysUsersRoleVo();
            sysUsersRoleVo.setUserId(sysUser.getId());
            sysUsersRoleVo.setUserName(sysUser.getUserName());
            if (userIds.contains(sysUser.getId())) {
                sysUsersRoleVo.setBelongCurRole(true);
            }
            sysUsersRoleVos.add(sysUsersRoleVo);
        }
        return sysUsersRoleVos;
    }

    @Override
    public boolean addRoleUsers(Long roleId, List<Long> userIds) {
        List<SysUserRole> sysUserRoleList = userIds.stream().map(userId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            return sysUserRole;
        }).collect(Collectors.toList());
        baseMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId,roleId));
        return saveOrUpdateBatch(sysUserRoleList);
    }
}
