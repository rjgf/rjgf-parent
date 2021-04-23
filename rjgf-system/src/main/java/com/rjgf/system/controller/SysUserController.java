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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.common.enums.StateEnum;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.service.ISysAreaService;
import com.rjgf.system.service.ISysUserService;
import com.rjgf.system.vo.req.SysUserQueryParam;
import com.rjgf.system.vo.req.UpdatePasswordParam;
import com.rjgf.system.vo.req.sysuser.AddSysUserParam;
import com.rjgf.system.vo.req.sysuser.UpdateSysUserParam;
import com.rjgf.system.vo.resp.SysUserInfoQueryVo;
import com.rjgf.system.vo.resp.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 系统用户 前端控制器
 * </pre>
 *
 * @author xula
 * @since 2019-10-24
 */
@ApiRestController("/sys/user")
@Api(value = "系统用户 API",tags = "系统用户")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;


    /**
     * 添加系统用户
     */
    @PostMapping("")
    @RequiresPermissions("sys:user:add")
    @ApiOperation(value = "添加SysUser对象", notes = "添加系统用户,权限路径(sys:user:add)")
    public R addSysUser(@Valid @RequestBody AddSysUserParam addSysUserParam) throws Exception {
        boolean flag = sysUserService.saveSysUser(addSysUserParam);
        return R.ok(flag);
    }

    /**
     * 修改系统用户
     */
    @PutMapping("/{id}")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改SysUser对象", notes = "修改系统用户,权限路径(sys:user:update)")
    public R updateSysUser(@Valid @RequestBody UpdateSysUserParam updateSysUserParam,@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.updateSysUser(updateSysUserParam.setId(id));
        return  R.ok(flag);
    }

    /**
     * 删除系统用户
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除SysUser对象", notes = "删除系统用户,权限路径(sys:user:delete)")
    public R deleteSysUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.deleteSysUser(id);
        return  R.ok(flag);
    }

    /**
     * 获取系统用户
     */
    @GetMapping("/{id}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation(value = "获取SysUser对象详情", notes = "查看系统用户,权限路径(sys:user:info)",response = SysUserInfoQueryVo.class)
    public R<SysUserInfoQueryVo> getSysUser(@PathVariable("id") Long id) throws Exception {
        SysUserInfoQueryVo sysUserQueryVo = sysUserService.getSysUserById(id);
        return R.ok(sysUserQueryVo);
    }

    /**
     * 系统用户分页列表
     */
    @PostMapping("/page")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "获取SysUser分页列表", notes = "系统用户分页列表,权限路径(sys:user:page)")
    public R<PageResponse<SysUserQueryVo>> getSysUserPageList(@Valid @RequestBody SysUserQueryParam sysUserQueryParam) throws Exception {
        IPage<SysUserQueryVo> paging = sysUserService.getSysUserPage(sysUserQueryParam);
        return R.page(paging);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password/{userId}")
    @RequiresPermissions("sys:user:password")
    @ApiOperation(value = "修改密码", notes = "修改密码,权限路径(sys:user:password)")
    public R updatePassword(@Valid @RequestBody UpdatePasswordParam updatePasswordParam,@PathVariable Long userId) throws Exception {
        boolean flag = sysUserService.updatePassword(updatePasswordParam.setUserId(userId));
        return R.ok(flag);
    }

    /**
     * 重置密码
     */
    @PutMapping("/password/reset/{userId}")
    @RequiresPermissions("sys:user:password:reset")
    @ApiOperation(value = "重置密码", notes = "重置密码,权限路径(sys:user:password:reset)")
    public R resetPassword(@Valid @PathVariable Long userId) throws Exception {
        boolean flag = sysUserService.resetPassword(userId);
        return R.ok(flag);
    }

    /**
     * 启用用户
     */
    @PutMapping("/state/enable/{id}")
    @RequiresPermissions("sys:user:state:enable")
    @ApiOperation(value = "启用用户", notes = "启用用户,权限路径(sys:user:state:enable)")
    public R stateEnable(@PathVariable("id") Long id) {
        sysUserService.changeUserState(id, StateEnum.ENABLE.getCode());
        return R.ok("");
    }

    /**
     * 停用用户
     */
    @PutMapping("/state/disable/{id}")
    @RequiresPermissions("sys:user:state:enable")
    @ApiOperation(value = "停用用户", notes = "停用用户,权限路径(sys:user:state:disable)")
    public R stateDisable(@PathVariable("id") Long id) {
        sysUserService.changeUserState(id,StateEnum.DISABLE.getCode());
        return R.ok("");
    }
}

