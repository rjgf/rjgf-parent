package com.rjgf.system.controller;


import com.rjgf.common.common.api.R;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.service.ISysAreaService;
import com.rjgf.system.vo.resp.SysAreaTreeVo;
import com.rjgf.system.vo.resp.SysDepartmentTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/api/sys/area")
@Api(value = "省市区 API",description = "省市区模块")
public class SysAreaController extends BaseController {

    @Autowired
    private ISysAreaService iSysAreaService;

    /**
     * 获取省市区的树形列表
     */
    @PostMapping("/tree")
//    @RequiresPermissions("sys:department:all:tree")
    @ApiOperation(value = "获取省市区的树形列表", notes = "获取省市区的树形列表")
    public R<List<SysAreaTreeVo>> getAllSysAreaTreeVo() {
        List<SysAreaTreeVo> treeVos = iSysAreaService.getSysAreaTree();
        return R.ok(treeVos);
    }
}
