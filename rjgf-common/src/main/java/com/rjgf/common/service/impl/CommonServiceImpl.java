package com.rjgf.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rjgf.common.service.CommonService;

/**
 * 自定义公共服务
 * @author xula
 * @date 2019/01/29
 */
public class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CommonService<T> {

}
