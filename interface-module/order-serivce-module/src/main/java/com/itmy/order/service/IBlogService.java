package com.itmy.order.service;


import com.itmy.entity.Blog;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-10 13:57
 */
public interface IBlogService {

    List<Blog> selectList();

    void insert(Blog blog);
}
