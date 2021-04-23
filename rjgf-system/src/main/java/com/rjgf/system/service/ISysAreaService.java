package com.rjgf.system.service;


import com.rjgf.common.service.CommonService;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.vo.resp.SysAreaTreeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
public interface ISysAreaService extends CommonService<SysArea> {

    /**
     * 获取省市区树
     * @return
     */
     List<SysAreaTreeVo> getSysAreaTree();
}
