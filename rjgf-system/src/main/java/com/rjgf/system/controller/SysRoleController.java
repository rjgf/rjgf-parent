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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysRolePermission;
import com.rjgf.system.entity.SysUser;
import com.rjgf.system.service.ISysRolePermissionService;
import com.rjgf.system.service.ISysRoleService;
import com.rjgf.system.service.ISysUserRoleService;
import com.rjgf.system.vo.req.SysRoleQueryParam;
import com.rjgf.system.vo.req.sysrole.AddSysRoleParam;
import com.rjgf.system.vo.req.sysrole.SysRolePermissionParam;
import com.rjgf.system.vo.req.sysrole.SysRoleUsersParam;
import com.rjgf.system.vo.req.sysrole.UpdateSysRoleParam;
import com.rjgf.system.vo.resp.SysRolePermissionVo;
import com.rjgf.system.vo.resp.SysRoleQueryVo;
import com.rjgf.system.vo.resp.SysUsersRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @author xula
 * @since 2019-10-24
 */
@ApiRestController("/sys/role")
@Api(value = "系统角色 API",tags = "系统角色")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRolePermissionService iSysRolePermissionService;
    @Autowired
    private ISysUserRoleService userRoleService;

    /**
     * 添加系统角色
     */
    @PostMapping("")
    @RequiresPermissions("sys:role:add")
    @ApiOperation(value = "添加SysRole对象", notes = "添加系统角色,权限路径(sys:role:add)")
    public R addSysRole(@Valid @RequestBody AddSysRoleParam addSysRoleParam) throws Exception {
        boolean flag = sysRoleService.saveSysRole(addSysRoleParam);
        return R.ok(flag);
    }

    /**
     * 修改系统角色
     */
    @PutMapping("/{id}")
    @RequiresPermissions("sys:role:update")
    @ApiOperation(value = "修改SysRole对象", notes = "修改系统角色,权限路径(sys:role:update)")
    public R updateSysRole(@Valid @RequestBody UpdateSysRoleParam updateSysRoleParam,@PathVariable("id") Long id) throws Exception {
        boolean flag = sysRoleService.updateSysRole(updateSysRoleParam.setId(id));
        return R.ok(flag);
    }

    /**
     * 删除系统角色
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:role:delete")
    @ApiOperation(value = "删除SysRole对象", notes = "删除系统角色,权限路径(sys:role:delete)")
    public R deleteSysRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysRoleService.deleteSysRole(id);
        return R.ok(flag);
    }

    /**
     * 获取系统角色
     */
    @GetMapping("/{id}")
    @RequiresPermissions("sys:role:info")
    @ApiOperation(value = "获取SysRole对象详情", notes = "查看系统角色,权限路径(sys:role:info)",response = SysRoleQueryVo.class)
    public R<SysRoleQueryVo> getSysRole(@PathVariable("id") Long id) throws Exception {
        SysRoleQueryVo sysRoleQueryVo = sysRoleService.getSysRoleById(id);
        return R.ok(sysRoleQueryVo);
    }

    /**
     * 系统角色分页列表
     */
    @PostMapping("/page")
    @RequiresPermissions("sys:role:page")
    @ApiOperation(value = "获取SysRole分页列表", notes = "系统角色分页列表,权限路径(sys:role:page)")
    public R<PageResponse<SysRoleQueryVo>> getSysRolePageList(@Valid @RequestBody SysRoleQueryParam sysRoleQueryParam) throws Exception {
        IPage<SysRoleQueryVo> paging = sysRoleService.getSysRolePage(sysRoleQueryParam);
        return R.page(paging);
    }

    /**
     * 系统角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    @ApiOperation(value = "获取SysRole列表", notes = "系统角色列表,权限路径(sys:role:list)")
    public R<List<SysRole>> getSysRoleList() {
        List<SysRole> sysRoles = sysRoleService.getSysRoleList();
        return R.ok(sysRoles);
    }

    /**
     * 获取角色的权限列表
     */
    @GetMapping("/permission/{id}")
    @RequiresPermissions("sys:role:permissions")
    @ApiOperation(value = "获取角色的权限列表", notes = "获取角色的权限列表,权限路径(sys:role:permissions)")
    public R<SysRolePermissionVo> getSysRoleList(@PathVariable("id") Long id) throws Exception {
        SysRolePermissionVo sysRolePermissionVo = sysRoleService.getRoleRolePermission(id);
        return R.ok(sysRolePermissionVo);
    }

    /**
     * 添加/更新 角色权限
     */
    @PutMapping("/permission/{id}")
    @RequiresPermissions("sys:role:permissions:update")
    @ApiOperation(value = "更新角色权限", notes = "更新角色权限,权限路径(sys:role:permissions:update)")
    public R updateRolePermissions(@PathVariable("id") Long id, @Valid @RequestBody SysRolePermissionParam sysRolePermissionParam) throws Exception {
        iSysRolePermissionService.updateSysRolePermission(sysRolePermissionParam.setRoleId(id));
        return R.ok("");
    }

    /**
     * 启用角色
     */
    @PutMapping("/state/enable/{id}")
    @RequiresPermissions("sys:role:state:enable")
    @ApiOperation(value = "启用角色", notes = "启用角色,权限路径 (sys:role:state:enable)")
    public R stateEnable(@PathVariable("id") Long id) {
        sysRoleService.changeRoleState(id, StateEnum.ENABLE.getCode());
        return R.ok("");
    }

    /**
     * 停用角色
     */
    @PutMapping("/state/disable/{id}")
    @RequiresPermissions("sys:role:state:disable")
    @ApiOperation(value = "停用角色", notes = "停用角色,权限路径 (sys:role:state:disable)")
    public R stateDisable(@PathVariable("id") Long id) {
        sysRoleService.changeRoleState(id,StateEnum.DISABLE.getCode());
        return R.ok("");
    }

    /**
     * 获取角色用户列表
     */
    @GetMapping("/users/{id}")
    @RequiresPermissions("sys:role:users")
    @ApiOperation(value = "获取角色用户列表", notes = "获取角色用户列表,权限路径 (sys:role:users)")
    public R<List<SysUsersRoleVo>> roleUsers(@PathVariable("id") Long id) {
        List<SysUsersRoleVo> sysUsers = userRoleService.getSysUserListByRole(id);
        return R.ok(sysUsers);
    }


    /**
     * 停用角色
     */
    @PutMapping("/users/{id}")
    @RequiresPermissions("sys:role:users:update")
    @ApiOperation(value = "更新角色用户信息", notes = "更新角色用户信息,权限路径 (sys:role:users:update)")
    public R roleUsersUpdate(@PathVariable("id") Long id, @RequestBody SysRoleUsersParam sysRoleUsersParam) {
        boolean result = userRoleService.addRoleUsers(id,sysRoleUsersParam.getUserIds());
        return R.ok(result);
    }
}

