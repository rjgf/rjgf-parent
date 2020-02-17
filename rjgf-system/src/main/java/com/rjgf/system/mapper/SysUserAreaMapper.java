package com.rjgf.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.entity.SysUserArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@Mapper
public interface SysUserAreaMapper extends BaseMapper<SysUserArea> {

    /**
     * 根据用户获取城市配置列表
     * @return
     */
    List<SysArea> getSysAreaList(Long userId);

}
