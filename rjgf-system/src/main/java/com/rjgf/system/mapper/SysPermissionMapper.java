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
import com.rjgf.system.entity.SysPermission;
import com.rjgf.system.vo.req.SysPermissionQueryParam;
import com.rjgf.system.vo.resp.SysPermissionQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 系统权限 Mapper 接口
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysPermissionQueryParam
     * @return
     */
    IPage<SysPermissionQueryVo> getSysPermissionPageList(@Param("page") IPage page, @Param("param") SysPermissionQueryParam sysPermissionQueryParam);

    /**
     * 根据用户id获取该用户所有权限编码
     *
     * @param userId
     * @return
     * @throws Exception
     */
    List<String> getPermissionCodesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id获取菜单列表
     *
     * @param userId
     * @return
     */
    List<SysPermission> getMenuListByUserId(@Param("userId") Long userId);
}
