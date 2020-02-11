package com.rjgf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysUserRole;
import com.rjgf.system.mapper.SysUserRoleMapper;
import com.rjgf.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
