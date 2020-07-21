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

package com.rjgf.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.api.resp.PageResponse;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.log.service.ISysLogService;
import com.rjgf.log.vo.req.SysLogQueryParam;
import com.rjgf.log.vo.resp.SysLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author xula
 * @since 2019-10-11
 */
@Slf4j
@ApiRestController("/sys/log")
@Api(value = "系统日志 API",tags = "系统日志")
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 删除系统日志
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:log:delete")
    @ApiOperation(value = "删除SysLog对象", notes = "删除系统日志,权限路径(sys:log:delete)")
    public R deleteSysLog(@Valid @PathVariable Long id) throws Exception {
        boolean flag = sysLogService.removeById(id);
        return R.ok(flag);
    }

    /**
     * 获取系统日志
     */
    @GetMapping("/{id}")
    @RequiresPermissions("sys:log:info")
    @ApiOperation(value = "获取SysLog对象详情", notes = "查看系统日志,权限路径(sys:log:info)")
    public R<SysLogQueryVo> getSysLog(@Valid @PathVariable Long id) throws Exception {
        SysLogQueryVo sysLogQueryVo = sysLogService.getSysLogById(id);
        return R.ok(sysLogQueryVo);
    }

    /**
     * 系统日志分页列表
     */
    @PostMapping("/page")
    @RequiresPermissions("sys:log:page")
    @ApiOperation(value = "获取SysLog分页列表", notes = "系统日志分页列表,权限路径(sys:log:page)")
    public R<PageResponse<SysLogQueryVo>> getSysLogPageList(@Valid @RequestBody SysLogQueryParam sysLogQueryParam) throws Exception {
        Page<SysLogQueryVo> paging = sysLogService.getSysLogPage(sysLogQueryParam);
        return R.page(paging);
    }
}

