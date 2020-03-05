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
import com.rjgf.system.entity.SysDepartment;
import com.rjgf.system.vo.req.SysDepartmentQueryParam;
import com.rjgf.system.service.ISysDepartmentService;
import com.rjgf.system.vo.resp.SysDepartmentQueryVo;
import com.rjgf.system.vo.resp.SysDepartmentTreeVo;
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
 * 部门 前端控制器
 * </pre>
 *
 * @author geekidea
 * @since 2019-10-24
 */
@Slf4j
@RestController
@RequestMapping("/sysDepartment")
@Api(value = "部门 API",description = "部门模块")
public class SysDepartmentController extends BaseController {

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    /**
     * 添加部门
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:department:add")
    @ApiOperation(value = "添加SysDepartment对象", notes = "添加部门")
    public R addSysDepartment(@Valid @RequestBody SysDepartment sysDepartment) throws Exception {
        boolean flag = sysDepartmentService.save(sysDepartment);
        return R.ok(flag);
    }

    /**
     * 修改部门
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:department:update")
    @ApiOperation(value = "修改SysDepartment对象", notes = "修改部门")
    public R updateSysDepartment(@Valid @RequestBody SysDepartment sysDepartment) throws Exception {
        boolean flag = sysDepartmentService.update(sysDepartment);
        return R.ok(flag);
    }

    /**
     * 删除部门
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:department:delete")
    @ApiOperation(value = "删除SysDepartment对象", notes = "删除部门")
    public R deleteSysDepartment(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysDepartmentService.delete(id);
        return R.ok(flag);
    }

    /**
     * 获取部门
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:department:info")
    @ApiOperation(value = "获取SysDepartment对象详情", notes = "查看部门",response = SysDepartmentQueryVo.class)
    public R<SysDepartmentQueryVo> getSysDepartment(@PathVariable("id") Long id) throws Exception {
        SysDepartmentQueryVo sysDepartmentQueryVo = sysDepartmentService.getSysDepartmentById(id);
        return R.ok(sysDepartmentQueryVo);
    }

    /**
     * 部门分页列表
     */
    @PostMapping("")
    @RequiresPermissions("sys:department:page")
    @ApiOperation(value = "获取SysDepartment分页列表", notes = "部门分页列表")
    public R<PageResponse<SysDepartmentQueryVo>> getSysDepartmentPageList(PageRequest page,@Valid @RequestBody SysDepartmentQueryParam sysDepartmentQueryParam) throws Exception {
        IPage<SysDepartmentQueryVo> paging = sysDepartmentService.getSysDepartmentPageList(sysDepartmentQueryParam);
        return R.page(paging);
    }

    /**
     * 获取所有部门列表
     */
    @PostMapping("/getAllDepartmentList")
//    @RequiresPermissions("sys:department:all:list")
    @ApiOperation(value = "获取所有部门的树形列表", notes = "获取所有部门的树形列表")
    public R<List<SysDepartment>> getAllDepartmentList() {
        List<SysDepartment> list = sysDepartmentService.getAllDepartmentList();
        return R.ok(list);
    }

    /**
     * 获取所有部门的树形列表
     */
    @PostMapping("/getAllDepartmentTree")
//    @RequiresPermissions("sys:department:all:tree")
    @ApiOperation(value = "获取所有部门的树形列表", notes = "获取所有部门的树形列表")
    public R<List<SysDepartmentTreeVo>> getAllDepartmentTree() {
        List<SysDepartmentTreeVo> treeVos = sysDepartmentService.getAllDepartmentTree();
        return R.ok(treeVos);
    }

}

