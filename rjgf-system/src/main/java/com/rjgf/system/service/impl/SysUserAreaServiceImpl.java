package com.rjgf.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.entity.SysUserArea;
import com.rjgf.system.mapper.SysUserAreaMapper;
import com.rjgf.system.service.ISysUserAreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@Service
public class SysUserAreaServiceImpl extends CommonServiceImpl<SysUserAreaMapper, SysUserArea> implements ISysUserAreaService {

    @Override
    public boolean addUserArea(Long userId, List<Integer> areaIds) {
        List<SysUserArea> sysUserAreaList = areaIds.stream().map(areaId -> {
            SysUserArea sysUserArea = new SysUserArea();
            sysUserArea.setAreaId(areaId);
            sysUserArea.setUserId(userId);
            return sysUserArea;
        }).collect(Collectors.toList());
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        baseMapper.delete(queryWrapper);
        return saveBatch(sysUserAreaList);
    }

    @Override
    public List<SysArea> getSysUserAreaList(Long userId) {
        return baseMapper.getSysAreaList(userId);
    }
}
