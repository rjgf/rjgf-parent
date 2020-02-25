/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
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

import cn.hutool.crypto.digest.DigestUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.auth.util.JwtUtil;
import com.rjgf.auth.util.LoginUtil;
import com.rjgf.common.common.exception.BusinessException;
import com.rjgf.common.common.exception.DaoException;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.common.util.jwt.SaltUtil;
import com.rjgf.system.convert.SysUserConvert;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.mapper.SysUserMapper;
import com.rjgf.system.service.*;
import com.rjgf.system.vo.req.SysUserQueryParam;
import com.rjgf.system.vo.req.UpdatePasswordParam;
import com.rjgf.system.vo.req.sysuser.AddSysUserParam;
import com.rjgf.system.vo.req.sysuser.UpdateSysUserParam;
import com.rjgf.system.vo.resp.SysUserInfoQueryVo;
import com.rjgf.system.vo.resp.SysUserQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * 系统用户 服务实现类
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@Service
public class SysUserServiceImpl extends CommonServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

//    @Autowired
//    private ISysDepartmentService sysDepartmentService;

    @Lazy
    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    @Autowired
    private ISysUserAreaService iSysUserAreaService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUser(AddSysUserParam addSysUserParam) throws Exception {
        // 校验用户名是否存在
        boolean isExists = isExistsByUsername(addSysUserParam.getUserName());
        if (isExists) {
            throw new BusinessException("用户名已存在");
        }
        // 校验部门和角色
        checkDepartmentAndRole(addSysUserParam.getDepartmentId(), addSysUserParam.getRoleIds());
        // 生成盐值
        String salt = SaltUtil.generateSalt();
        SysUser sysUser = SysUserConvert.INSTANCE.addSysUserParamToSysUser(addSysUserParam);
        sysUser.setSalt(salt);
        // 密码加密
        String newPassword = DigestUtil.sha256Hex(sysUser.getPassword() + salt);
        sysUser.setPassword(newPassword);
        sysUser.setCreateId(LoginUtil.getUserId());
        // 保存系统用户
        boolean result = super.save(sysUser);
        if (!result) {
            throw new DaoException("用户添加失败！");
        }
        Long userId = sysUser.getId();
        // 保存用户的角色信息
        result = iSysUserRoleService.addUserRole(userId,addSysUserParam.getRoleIds());
        if (!result) {
            throw new DaoException("保存用户的角色信息失败！");
        }
        // 保存用户城市配置
        result = iSysUserAreaService.addUserArea(userId,addSysUserParam.getAreaIds());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUser(UpdateSysUserParam updateSysUserParam) throws Exception {
        // 校验部门和角色
        checkDepartmentAndRole(updateSysUserParam.getDepartmentId(), updateSysUserParam.getRoleIds());
        // 获取系统用户
        SysUser updateSysUser = getById(updateSysUserParam.getId());
        if (updateSysUser == null) {
            throw new BusinessException("修改的用户不存在");
        }
        // 修改系统用户
        updateSysUser.setRealName(updateSysUserParam.getRealName())
                .setPhone(updateSysUserParam.getPhone())
                .setGender(updateSysUserParam.getGender())
                .setRemark(updateSysUserParam.getRemark())
                .setState(updateSysUserParam.getState())
                .setDepartmentId(updateSysUserParam.getDepartmentId())
                .setUpdateTime(new Date())
                .setUpdateId(LoginUtil.getUserId());

        Long userId = updateSysUserParam.getId();
        // 保存系统用户
        boolean result = super.updateById(updateSysUser);
        if (!result) {
            throw new DaoException("修改添加失败！");
        }
        // 保存用户的角色信息
        result = iSysUserRoleService.addUserRole(userId,updateSysUserParam.getRoleIds());
        if (!result) {
            throw new DaoException("保存用户的角色信息失败！");
        }
        // 保存用户城市配置
        result = iSysUserAreaService.addUserArea(userId,updateSysUserParam.getAreaIds());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public SysUserInfoQueryVo getSysUserById(Long id) throws Exception {
        SysUserInfoQueryVo sysUserInfoQueryVo =  sysUserMapper.getSysUserById(id);
        // 获取用户的角色
        sysUserInfoQueryVo.setSysRoles(iSysUserRoleService.getSysUserRoleList(id));
        // 获取用户的城市配置
        sysUserInfoQueryVo.setSysAreas(iSysUserAreaService.getSysUserAreaList(id));
        return sysUserInfoQueryVo;
    }

    @Override
    public IPage<SysUserQueryVo> getSysUserPage(SysUserQueryParam sysUserQueryParam, IPage page) throws Exception {
        return sysUserMapper.getSysUserPageList(page, sysUserQueryParam);
    }

    @Override
    public boolean isExistsByUsername(String username) throws Exception {
        SysUser selectSysUser = new SysUser().setUserName(username);
        return sysUserMapper.selectCount(new QueryWrapper<>(selectSysUser)) > 0;
    }

    @Override
    public void checkDepartmentAndRole(Long departmentId, List<Long> roleId) throws Exception {
        // 校验部门是否存在并且可用
//        boolean isEnableDepartment = sysDepartmentService.isEnableSysDepartment(departmentId);
//        if (!isEnableDepartment) {
//            throw new BusinessException("该部门不存在或已禁用");
//        }
        // 校验角色是否存在并且可用
        boolean isEnableRole = sysRoleService.isEnableSysRole(roleId);
        if (!isEnableRole) {
            throw new BusinessException("该角色不存在或已禁用");
        }
    }

    @Override
    public boolean isExistsSysUserByRoleId(Long roleId) throws Exception {
        List<SysUser> sysUserList = sysUserMapper.getSysUsersByRoleId(roleId);
        long count = sysUserList.stream().filter(s -> StateEnum.ENABLE.getCode().equals(s.getState())).count();
        return count > 0;
    }

    @Override
    public boolean updatePassword(UpdatePasswordParam updatePasswordParam) throws Exception {
        String oldPassword = updatePasswordParam.getOldPassword();
        String newPassword = updatePasswordParam.getNewPassword();
        String confirmPassword = updatePasswordParam.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new BusinessException("两次输入的密码不一致");
        }
        if (newPassword.equals(oldPassword)) {
            throw new BusinessException("新密码和旧密码不能一致");
        }

        // 判断原密码是否正确
        SysUser sysUser = getById(updatePasswordParam.getUserId());
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (StateEnum.DISABLE.getCode().equals(sysUser.getState())) {
            throw new BusinessException("用户已禁用");
        }
        // 密码加密处理
        String salt = sysUser.getSalt();
        String encryptOldPassword = DigestUtil.sha256Hex(oldPassword, salt);
        if (!sysUser.getPassword().equals(encryptOldPassword)) {
            throw new BusinessException("原密码错误");
        }
        // 新密码加密
        String encryptNewPassword = DigestUtil.sha256Hex(newPassword + salt);

        // 修改密码
        sysUser.setPassword(encryptNewPassword)
                .setUpdateTime(new Date());
        return updateById(sysUser);
    }


    @Override
    public boolean resetPassword(Long userId) throws Exception {
        SysUser sysUser = getById(userId);
        // 新密码加密
        String encryptNewPassword = DigestUtil.sha256Hex(JwtUtil.getDefaultPwd() + sysUser.getSalt());
        SysUser sysUser1 = new SysUser()
                .setId(userId)
                .setPassword(encryptNewPassword);
        return updateById(sysUser1);
    }

    @Override
    public SysUser getSysUserByUsername(String userName) {
        SysUser sysUser = new SysUser().setUserName(userName);
        return getOne(new QueryWrapper(sysUser));
    }
}
