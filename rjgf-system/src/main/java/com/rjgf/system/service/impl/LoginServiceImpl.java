package com.rjgf.system.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.auth.api.service.ILoginService;
import com.rjgf.auth.api.vo.LoginSysUserVo;
import com.rjgf.system.convert.SysUserConvert;
import com.rjgf.system.entity.SysDepartment;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.service.ISysDepartmentService;
import com.rjgf.system.service.ISysRolePermissionService;
import com.rjgf.system.service.ISysUserRoleService;
import com.rjgf.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录接口实现
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/2
 * @time: 14:36
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Autowired
    private ISysUserRoleService iSysUserRoleService;


    @Override
    public LoginSysUserVo login(String userName, String password) throws Exception {
        // 从数据库中获取登陆用户信息
        SysUser sysUser = iSysUserService.getSysUserByUsername(userName);
        if (sysUser == null) {
            log.error("登陆失败,userName:{}", userName);
            throw new AuthenticationException("用户名或密码错误");
        }
        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
            throw new AuthenticationException("账号已禁用");
        }
        // 实际项目中，前端传过来的密码应先加密
        // 原始密码明文：123456
        // 原始密码前端加密：sha256(123456)
        // 后台加密规则：sha256(sha256(123456) + salt)
        String encryptPassword = DigestUtil.sha256Hex(password + sysUser.getSalt());
        if (!encryptPassword.equals(sysUser.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        // 将系统用户对象转换成登陆用户对象
        LoginSysUserVo loginSysUserVo = SysUserConvert.INSTANCE.sysUserToLoginSysUserVo(sysUser);
        setUserSystemInfo(sysUser, loginSysUserVo);
        return loginSysUserVo;
    }

    /**
     * 设置用户的部门，角色，用户权限
     * @param sysUser
     * @param loginSysUserVo
     */
    private void setUserSystemInfo(SysUser sysUser, LoginSysUserVo loginSysUserVo) throws Exception {
        // 获取部门
        SysDepartment sysDepartment = sysDepartmentService.getById(sysUser.getDepartmentId());
        if (sysDepartment == null) {
            throw new AuthenticationException("部门不存在");
        }
        if (!StateEnum.ENABLE.getCode().equals(sysDepartment.getState())) {
            throw new AuthenticationException("部门已禁用");
        }
        loginSysUserVo.setDepartmentId(sysDepartment.getId())
                .setDepartmentName(sysDepartment.getName());

        // 获取当前用户角色
        List<SysRole> sysRoles = iSysUserRoleService.getSysUserRoleList(sysUser.getId());
        if (CollectionUtils.isEmpty(sysRoles)) {
            throw new AuthenticationException("角色不存在");
        }
        List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        Set<String> roleCodes = sysRoles.stream().map(SysRole::getCode).collect(Collectors.toSet());
        loginSysUserVo.setSysRoleCodes(roleCodes);
        // 获取当前用户权限
        Set<String> permissionCodes = sysRolePermissionService.getPermissionCodesByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(permissionCodes)) {
            throw new AuthenticationException("权限列表不能为空");
        }
        loginSysUserVo.setPermissionCodes(permissionCodes);
    }
}
