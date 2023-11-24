package com.itmy.order.service.impl;

import com.itmy.entity.Blog;
import com.itmy.order.dao.BlogDao;
import com.itmy.order.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-10 13:57
 */
@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Blog> selectList() {
        return blogDao.selectList();
    }

    @Override
    public void insert(Blog blog) {
        blogDao.insert(blog);
    }
}
