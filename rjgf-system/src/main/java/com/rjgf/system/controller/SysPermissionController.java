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

package com.rjgf.system.controller;

import com.rjgf.auth.util.LoginUtil;
import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.system.entity.SysPermission;
import com.rjgf.system.service.ISysPermissionService;
import com.rjgf.system.vo.resp.SysPermissionQueryVo;
import com.rjgf.system.vo.resp.SysPermissionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @author xula
 * @since 2019-10-24
 */
@ApiRestController("/sys/permission")
@Api(value = "系统权限 API",tags = "系统权限")
public class SysPermissionController extends BaseController {

    @Autowired
    private ISysPermissionService sysPermissionService;

    /**
     * 添加系统权限
     */
    @PostMapping("")
    @RequiresPermissions("sys:permission:add")
    @ApiOperation(value = "添加SysPermission对象", notes = "添加系统权限，权限路径(sys:permission:add)")
    public R addSysPermission(@Valid @RequestBody SysPermission sysPermission) throws Exception {
        boolean flag = sysPermissionService.saveSysPermission(sysPermission);
        return R.ok(flag);
    }

    /**
     * 修改系统权限
     */
    @PutMapping("{id}")
    @RequiresPermissions("sys:permission:update")
    @ApiOperation(value = "修改SysPermission对象", notes = "修改系统权限，权限路径(sys:permission:update)")
    public R updateSysPermission(@Valid @RequestBody SysPermission sysPermission,@PathVariable("id") Long id) throws Exception {
        boolean flag = sysPermissionService.updateSysPermission(sysPermission.setId(id));
        return R.ok(flag);
    }

    /**
     * 删除系统权限
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:permission:delete")
    @ApiOperation(value = "删除SysPermission对象", notes = "删除系统权限，权限路径(sys:permission:delete)")
    public R deleteSysPermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysPermissionService.deleteSysPermission(id);
        return R.ok(flag);
    }

    /**
     * 获取系统权限
     */
    @GetMapping("/{id}")
    @RequiresPermissions("sys:permission:info")
    @ApiOperation(value = "获取SysPermission对象详情", notes = "查看系统权限，权限路径(sys:permission:info)",response = SysPermissionQueryVo.class)
    public R<SysPermissionQueryVo> getSysPermission(@PathVariable("id") Long id) throws Exception {
        SysPermissionQueryVo sysPermissionQueryVo = sysPermissionService.getSysPermissionById(id);
        return R.ok(sysPermissionQueryVo);
    }

    /**
     * 获取获取菜单树形列表
     */
    @GetMapping("/tree")
    @RequiresPermissions("sys:permission:tree")
    @ApiOperation(value = "获取所有的菜单树", notes = "获取所有的菜单树，权限路径(sys:permission:tree)")
    public R<List<SysPermissionTreeVo>> getAllMenuTree() throws Exception {
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getAllMenuTree();
        return R.ok(treeVos);
    }

    /**
     * 获取当前用户的权限树
     */
    @GetMapping("/user/tree")
    @ApiOperation(value = "当前用户的权限树", notes = "当前用户的权限树，权限路径(sys:permission:user:tree)")
    public R<List<SysPermissionTreeVo>> getUserMenuTree() throws Exception {
        List<SysPermissionTreeVo> treeVos = sysPermissionService.getMenuTreeByUserId(LoginUtil.getUserId());
        return R.ok(treeVos);
    }

    /**
     * 启用菜单
     */
    @PutMapping("/state/enable/{id}")
    @RequiresPermissions("sys:permission:state:enable")
    @ApiOperation(value = "启用菜单", notes = "启用菜单，权限路径(sys:permission:state:enable)")
    public R stateEnable(@PathVariable("id") Long id) {
        sysPermissionService.changeMenuState(id, StateEnum.ENABLE.getCode());
        return R.ok("");
    }

    /**
     * 停用菜单
     */
    @PutMapping("/state/disable/{id}")
    @RequiresPermissions("sys:permission:state:disable")
    @ApiOperation(value = "停用菜单", notes = "停用菜单，权限路径(sys:permission:state:disable)")
    public R stateDisable(@PathVariable("id") Long id) {
        sysPermissionService.changeMenuState(id,StateEnum.DISABLE.getCode());
        return R.ok("");
    }
}

