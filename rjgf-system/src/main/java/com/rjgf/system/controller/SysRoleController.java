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

package com.rjgf.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.service.ISysRoleService;
import com.rjgf.system.vo.req.SysRoleQueryParam;
import com.rjgf.system.vo.req.sysrole.AddSysRoleParam;
import com.rjgf.system.vo.req.sysrole.UpdateSysRoleParam;
import com.rjgf.system.vo.resp.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 系统角色 前端控制器
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/role")
@Api(value = "系统角色 API",description = "系统角色模块")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 添加系统角色
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:role:add")
    @ApiOperation(value = "添加SysRole对象", notes = "添加系统角色")
    public R addSysRole(@Valid @RequestBody AddSysRoleParam addSysRoleParam) throws Exception {
        boolean flag = sysRoleService.saveSysRole(addSysRoleParam);
        return R.ok(flag);
    }

    /**
     * 修改系统角色
     */
    @PutMapping("/update")
    @RequiresPermissions("sys:role:update")
    @ApiOperation(value = "修改SysRole对象", notes = "修改系统角色")
    public R updateSysRole(@Valid @RequestBody UpdateSysRoleParam updateSysRoleParam) throws Exception {
        boolean flag = sysRoleService.updateSysRole(updateSysRoleParam);
        return R.ok(flag);
    }

    /**
     * 删除系统角色
     */
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = "删除SysRole对象", notes = "删除系统角色")
    public R deleteSysRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysRoleService.deleteSysRole(id);
        return R.ok(flag);
    }

    /**
     * 获取系统角色
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:role:info")
    @ApiOperation(value = "获取SysRole对象详情", notes = "查看系统角色",response = SysRoleQueryVo.class)
    public R<SysRoleQueryVo> getSysRole(@PathVariable("id") Long id) throws Exception {
        SysRoleQueryVo sysRoleQueryVo = sysRoleService.getSysRoleById(id);
        return R.ok(sysRoleQueryVo);
    }

    /**
     * 系统角色分页列表
     */
    @PostMapping("")
    @RequiresPermissions("sys:role")
    @ApiOperation(value = "获取SysRole分页列表", notes = "系统角色分页列表")
    public R<PageResponse<SysRoleQueryVo>> getSysRolePageList(@Valid @RequestBody SysRoleQueryParam sysRoleQueryParam, Page page) throws Exception {
        IPage<SysRoleQueryVo> paging = sysRoleService.getSysRolePage(sysRoleQueryParam,page);
        return R.page(paging);
    }

    /**
     * 系统角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    @ApiOperation(value = "获取SysRole列表", notes = "系统角色列表")
    public R<List<SysRole>> getSysRoleList() {
        List<SysRole> sysRoles = sysRoleService.getSysRoleList();
        return R.ok(sysRoles);
    }

}

