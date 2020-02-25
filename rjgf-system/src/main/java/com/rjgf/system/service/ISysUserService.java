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

package com.rjgf.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.service.CommonService;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.vo.req.SysUserQueryParam;
import com.rjgf.system.vo.req.UpdatePasswordParam;
import com.rjgf.system.vo.req.sysuser.AddSysUserParam;
import com.rjgf.system.vo.req.sysuser.UpdateSysUserParam;
import com.rjgf.system.vo.resp.SysUserInfoQueryVo;
import com.rjgf.system.vo.resp.SysUserQueryVo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统用户 服务类
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
public interface ISysUserService extends CommonService<SysUser> {

    /**
     * 保存
     *
     * @param addSysUserParam
     * @return
     * @throws Exception
     */
    boolean saveSysUser(AddSysUserParam addSysUserParam) throws Exception;

    /**
     * 修改
     *
     * @param updateSysUserParam
     * @return
     * @throws Exception
     */
    boolean updateSysUser(UpdateSysUserParam updateSysUserParam) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysUser(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysUserInfoQueryVo getSysUserById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysUserQueryParam
     * @return
     * @throws Exception
     */
    IPage<SysUserQueryVo> getSysUserPage(SysUserQueryParam sysUserQueryParam, IPage page) throws Exception;

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     * @throws Exception
     */
    boolean isExistsByUsername(String username) throws Exception;

    /**
     * 检验部门和角色是否存在并且已启用
     *
     * @param departmentId
     * @param roleIds
     * @throws Exception
     */
    void checkDepartmentAndRole(Long departmentId, List<Long> roleIds) throws Exception;

    /**
     * 通过角色id判断是否存在可用用户id
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    boolean isExistsSysUserByRoleId(Long roleId) throws Exception;

    /**
     * 修改密码
     *
     * @param updatePasswordParam
     * @return
     * @throws Exception
     */
    boolean updatePassword(UpdatePasswordParam updatePasswordParam) throws Exception;

    /**
     * 重置密码
     * @param
     * @param userId
     * @return
     * @throws Exception
     */
    boolean resetPassword(Long userId) throws Exception;

    /**
     * 获取用户信息
     * @param userName 用户名
     * @return
     */
    SysUser getSysUserByUsername(String userName);
}
