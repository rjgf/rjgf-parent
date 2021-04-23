package com.rjgf.system.service;


import com.rjgf.common.service.CommonService;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.entity.SysRole;
import com.rjgf.system.entity.SysUserArea;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
public interface ISysUserAreaService extends CommonService<SysUserArea> {

    /**
     * 添加或更新用户角色信息
     * @param userId 用户编号
     * @param codes 城市配置列表
     * @return
     */
    boolean addUserArea(Long userId, List<Integer> codes);


    /**
     * 获取用户的城市配置列表
     * @param userId 用户编号
     * @return
     */
    List<SysArea> getSysUserAreaList(Long userId);
}
