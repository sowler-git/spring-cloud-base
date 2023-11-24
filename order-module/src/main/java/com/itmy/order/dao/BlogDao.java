package com.itmy.order.dao;

import com.itmy.entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-10 13:55
 */
@Mapper
public interface BlogDao {

    @Select("select * from tb_blog")
    List<Blog> selectList();

    @Insert("insert into tb_blog " +
            "(uid,title,summary,content,tag_uid) " +
            "values " +
            "(#{uid},#{title},#{summary},#{content},#{tagUid})")
    void insert(Blog blog);
}
