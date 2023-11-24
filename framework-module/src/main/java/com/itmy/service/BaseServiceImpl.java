package com.itmy.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:13
 */
public abstract class BaseServiceImpl<E extends BaseMapper<T>, T> extends ServiceImpl<E,T> implements IService<T> {

}
