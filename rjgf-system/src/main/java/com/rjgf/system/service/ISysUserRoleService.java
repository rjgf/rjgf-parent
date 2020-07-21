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

package com.rjgf.system.service;



import com.rjgf.common.service.CommonService;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.entity.SysUserRole;
import com.rjgf.system.vo.resp.SysUsersRoleVo;

import java.util.List;

/**
 * <pre>
 * 系统角色 服务类
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
public interface ISysUserRoleService extends CommonService<SysUserRole> {

    /**
     * 获取用户的角色列表
     * @param userId 用户编号
     * @return
     */
    List<SysRole> getSysUserRoleList(Long userId);


    /**
     * 添加或更新用户角色信息
     * @param userId 用户编号
     * @param roleIds 角色列表
     * @return
     */
    boolean addUserRole(Long userId, List<Long> roleIds);


    /**
     * 获取所属该角色用户列表
     * @param roleId
     * @return
     */
    List<SysUsersRoleVo> getSysUserListByRole(Long roleId);


    /**
     * 添加或更新用户角色信息
     * @param roleId 角色编号
     * @param userIds 用户列表
     * @return
     */
    boolean addRoleUsers(Long roleId, List<Long> userIds);
}
