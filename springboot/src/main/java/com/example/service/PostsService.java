package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PostsService extends IService<Posts> {

    void add(Posts posts);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Posts selectById(Integer id);

    List<Posts> selectAll(Posts posts);

    Page<Posts> selectPage(Posts posts, Integer page, Integer size);

}
