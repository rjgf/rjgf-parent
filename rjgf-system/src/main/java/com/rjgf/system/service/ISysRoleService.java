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
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.vo.req.SysRoleQueryParam;
import com.rjgf.system.vo.req.sysrole.AddSysRoleParam;
import com.rjgf.system.vo.req.sysrole.UpdateSysRoleParam;
import com.rjgf.system.vo.resp.SysRoleQueryVo;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统角色 服务类
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
public interface ISysRoleService extends CommonService<SysRole> {

    /**
     * 保存
     *
     * @param addSysRoleParam
     * @return
     * @throws Exception
     */
    boolean saveSysRole(AddSysRoleParam addSysRoleParam) throws Exception;

    /**
     * 修改
     *
     * @param updateSysRoleParam
     * @return
     * @throws Exception
     */
    boolean updateSysRole(UpdateSysRoleParam updateSysRoleParam) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysRole(Long id) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysRoleQueryVo getSysRoleById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysRoleQueryParam
     * @return
     * @throws Exception
     */
    IPage<SysRoleQueryVo> getSysRolePage(SysRoleQueryParam sysRoleQueryParam, Page page) throws Exception;

    /**
     * 根据id校验角色是否存在并且已启用
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean isEnableSysRole(Long id) throws Exception;

    /**
     * 根据id校验角色是否存在并且已启用
     *
     * @param ids
     * @return
     * @throws Exception
     */
    boolean isEnableSysRole(List<Long> ids) throws Exception;

    /**
     * 判断角色编码是否存在
     * @param code
     * @return
     * @throws Exception
     */
    boolean isExistsByCode(String code) throws Exception;


    /**
     * 获取可用的角色
     * @return
     */
    List<SysRole> getSysRoleList();

}
