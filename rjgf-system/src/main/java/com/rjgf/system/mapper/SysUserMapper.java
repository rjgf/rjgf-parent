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

package com.rjgf.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.vo.req.SysUserQueryParam;
import com.rjgf.system.vo.resp.SysUserInfoQueryVo;
import com.rjgf.system.vo.resp.SysUserQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统用户 Mapper 接口
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysUserInfoQueryVo getSysUserById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysUserQueryParam
     * @return
     */
    IPage<SysUserQueryVo> getSysUserPageList(@Param("page") IPage page, @Param("param") SysUserQueryParam sysUserQueryParam);


    /**
     * 获取属于该角色的用户列表
     * @param roleId
     * @return
     */
    List<SysUser> getSysUsersByRoleId(Long roleId);
}
