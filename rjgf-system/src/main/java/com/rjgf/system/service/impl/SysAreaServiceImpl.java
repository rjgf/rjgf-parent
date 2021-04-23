package com.rjgf.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rjgf.common.service.impl.CommonServiceImpl;
import com.rjgf.system.convert.SysAreaConvert;
import com.rjgf.system.entity.SysArea;
import com.rjgf.system.mapper.SysAreaMapper;
import com.rjgf.system.service.ISysAreaService;
import com.rjgf.system.vo.resp.SysAreaTreeVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xula
 * @since 2020-02-13
 */
@CacheConfig(cacheNames = "my-redis-cache2")
@Service
public class SysAreaServiceImpl extends CommonServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Cacheable
    @Override
    public List<SysAreaTreeVo> getSysAreaTree() {
        List<SysArea> sysAreas = getBaseSysArea(21);
        return convertSysArea(sysAreas);
    }

    private List<SysAreaTreeVo> convertSysArea(List<SysArea> sysAreas) {
        List<SysAreaTreeVo> sysAreaTreeVos = SysAreaConvert.INSTANCE.listToTreeVoList(sysAreas);
        for (SysAreaTreeVo sysAreaTreeVo:sysAreaTreeVos) {
            List<SysArea> sysAreas1 = getBaseSysArea(sysAreaTreeVo.getId());
            if (CollectionUtils.isNotEmpty(sysAreas)) {
                sysAreaTreeVo.setChildren(convertSysArea(sysAreas1));
            }
        }
        return sysAreaTreeVos;
    }

    private List<SysArea> getBaseSysArea(Integer id) {
        SysArea sysArea = new SysArea().setParentId(id);
        QueryWrapper queryWrapper = new QueryWrapper(sysArea);
        return baseMapper.selectList(queryWrapper);
    }

}
