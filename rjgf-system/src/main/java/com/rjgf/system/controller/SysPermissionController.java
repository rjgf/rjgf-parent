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
import com.rjgf.common.common.api.req.PageRequest;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.auth.util.LoginUtil;
import com.rjgf.system.entity.SysPermission;
import com.rjgf.system.service.ISysPermissionService;
import com.rjgf.system.vo.req.SysPermissionQueryParam;
import com.rjgf.system.vo.resp.SysPermissionQueryVo;
import com.rjgf.system.vo.resp.SysPermissionTreeVo;
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
 * 系统权限 前端控制器
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/permission")
@Api(value = "系统权限 API",description = "系统权限")
public class SysPermissionController extends BaseController {

    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 添加系统权限
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:permission:add")
    @ApiOperation(value = "添加SysPermission对象", notes = "添加系统权限")
    public R addSysPermission(@Valid @RequestBody SysPermission sysPermission) throws Exception {
        boolean flag = sysPermissionService.saveSysPermission(sysPermission);
        return R.ok(flag);
    }

    /**
     * 修改系统权限
     */
    @PutMapping("/update")
    @RequiresPermissions("sys:permission:update")
    @ApiOperation(value = "修改SysPermission对象", notes = "修改系统权限")
    public R updateSysPermission(@Valid @RequestBody SysPermission sysPermission) throws Exception {
        boolean flag = sysPermissionService.updateSysPermission(sysPermission);
        return R.ok(flag);
    }

    /**
     * 删除系统权限
     */
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:permission:delete")
    @ApiOperation(value = "删除SysPermission对象", notes = "删除系统权限")
    public R deleteSysPermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysPermissionService.deleteSysPermission(id);
        return R.ok(flag);
    }

    /**
     * 获取系统权限
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:permission:info")
    @ApiOperation(value = "获取SysPermission对象详情", notes = "查看系统权限",response = SysPermissionQueryVo.class)
    public R<SysPermissionQueryVo> getSysPermission(@PathVariable("id") Long id) throws Exception {
        SysPermissionQueryVo sysPermissionQueryVo = sysPermissionService.getSysPermissionById(id);
        return R.ok(sysPermissionQueryVo);
    }

    /**
     * 系统权限分页列表
     */
    @PostMapping("")
    @RequiresPermissions("sys:permission")
    @ApiOperation(value = "获取SysPermission分页列表", notes = "系统权限分页列表")
    public R<PageResponse<SysPermissionQueryVo>> getSysPermissionPageList(@Valid @RequestBody SysPermissionQueryParam sysPermissionQueryParam) throws Exception {
        IPage<SysPermissionQueryVo> paging = sysPermissionService.getSysPermissionPage(sysPermissionQueryParam);
        return R.page(paging);
    }

    /**
     * 获取所有菜单列表
     */
    @GetMapping("/allMenus")
    @RequiresPermissions("sys:permission:all:menus")
    @ApiOperation(value = "获取所有菜单列表", notes = "获取所有菜单列表")
    public R<List<SysPermission>> getAllMenuList() throws Exception {
        List<SysPermission> list = sysPermissionService.getAllMenuList();
        return R.ok(list);
    }

    /**
     * 获取获取菜单树形列表
     */
    @GetMapping("/allMenuTree")
    @RequiresPermissions("sys:permission:all:menu:tree")
    @ApiOperation(value = "获取所有菜单列表", notes = "获取所有菜单列表")
    public R<List<SysPermissionTreeVo>> getAllMenuTree() throws Exception {
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getAllMenuTree();
        return R.ok(treeVos);
    }


    /**
     * 获取当前用户菜单列表
     */
    @GetMapping("/menus")
    @RequiresPermissions("sys:permission:menus")
    @ApiOperation(value = "获取当前用户菜单列表", notes = "获取当前用户菜单列表")
    public R<List<SysPermission>> getMenuList() throws Exception {
        Long userId = LoginUtil.getUserId();
        List<SysPermission> list = sysPermissionService.getMenuListByUserId(userId);
        return R.ok(list);
    }

    /**
     * 获取当前用户菜单树形列表
     */
    @GetMapping("/menuTree")
    @RequiresPermissions("sys:permission:menu:tree")
    @ApiOperation(value = "获取当前用户菜单树形列表", notes = "获取当前用户菜单树形列表")
    public R<List<SysPermissionTreeVo>> getMenuTree() throws Exception {
        Long userId = LoginUtil.getUserId();
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getMenuTreeByUserId(userId);
        return R.ok(treeVos);
    }

    /**
     * 根据用户id获取该用户所有权限编码
     */
    @GetMapping("/permissionCodes")
    @RequiresPermissions("sys:permission:codes")
    @ApiOperation(value = "根据当前用户获取该用户所有权限编码", notes = "根据当前用户获取该用户所有权限编码")
    public R<List<String>> getPermissionCodesByUserId() throws Exception {
        Long userId = LoginUtil.getUserId();
        List<String> list = sysPermissionService.getPermissionCodesByUserId(userId);
        return R.ok(list);
    }

}

