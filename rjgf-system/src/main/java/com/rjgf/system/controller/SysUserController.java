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
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.api.req.PageRequest;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.system.service.ISysUserService;
import com.rjgf.system.vo.req.SysUserQueryParam;
import com.rjgf.system.vo.req.UpdatePasswordParam;
import com.rjgf.system.vo.req.sysuser.AddSysUserParam;
import com.rjgf.system.vo.req.sysuser.UpdateSysUserParam;
import com.rjgf.system.vo.resp.SysUserInfoQueryVo;
import com.rjgf.system.vo.resp.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <pre>
 * 系统用户 前端控制器
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(value = "系统用户 API",description = "系统用户模块")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 添加系统用户
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:user:add")
    @ApiOperation(value = "添加SysUser对象", notes = "添加系统用户")
    public R addSysUser(@Valid @RequestBody AddSysUserParam addSysUserParam) throws Exception {
        boolean flag = sysUserService.saveSysUser(addSysUserParam);
        return R.ok(flag);
    }

    /**
     * 修改系统用户
     */
    @PutMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改SysUser对象", notes = "修改系统用户")
    public R updateSysUser(@Valid @RequestBody UpdateSysUserParam updateSysUserParam) throws Exception {
        boolean flag = sysUserService.updateSysUser(updateSysUserParam);
        return  R.ok(flag);
    }

    /**
     * 删除系统用户
     */
    @DeleteMapping("/delete/{id}")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除SysUser对象", notes = "删除系统用户")
    public R deleteSysUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysUserService.deleteSysUser(id);
        return  R.ok(flag);
    }

    /**
     * 获取系统用户
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation(value = "获取SysUser对象详情", notes = "查看系统用户",response = SysUserInfoQueryVo.class)
    public R<SysUserInfoQueryVo> getSysUser(@PathVariable("id") Long id) throws Exception {
        SysUserInfoQueryVo sysUserQueryVo = sysUserService.getSysUserById(id);
        return R.ok(sysUserQueryVo);
    }

    /**
     * 系统用户分页列表
     */
    @PostMapping("")
    @RequiresPermissions("sys:user")
    @ApiOperation(value = "获取SysUser分页列表", notes = "系统用户分页列表")
    public R<PageResponse<SysUserQueryVo>> getSysUserPageList(@Valid @RequestBody SysUserQueryParam sysUserQueryParam) throws Exception {
        IPage<SysUserQueryVo> paging = sysUserService.getSysUserPage(sysUserQueryParam);
        return R.page(paging);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    @RequiresPermissions("sys:user:update:password")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordParam updatePasswordParam) throws Exception {
        boolean flag = sysUserService.updatePassword(updatePasswordParam);
        return R.ok(flag);
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPassword/{userId}")
//    @RequiresPermissions("sys:user:update:password")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public R resetPassword(@Valid @PathVariable Long userId) throws Exception {
        boolean flag = sysUserService.resetPassword(userId);
        return R.ok(flag);
    }
}

