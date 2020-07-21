package com.rjgf.system.controller;


import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.common.controller.BaseController;
import com.rjgf.system.service.ISysAreaService;
import com.rjgf.system.vo.resp.SysAreaTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@ApiRestController("/sys/area")
@Api(value = "省市区 API",tags = "省市区")
public class SysAreaController extends BaseController {

    @Autowired
    private ISysAreaService iSysAreaService;

    /**
     * 获取省市区的树形列表
     */
    @PostMapping("/tree")
//    @RequiresPermissions("sys:area:tree")
    @ApiOperation(value = "获取省市区的树形列表", notes = "获取省市区的树形列表，权限路径(sys:area:tree)")
    public R<List<SysAreaTreeVo>> getAllSysAreaTreeVo() {
        List<SysAreaTreeVo> treeVos = iSysAreaService.getSysAreaTree();
        return R.ok(treeVos);
    }
}
