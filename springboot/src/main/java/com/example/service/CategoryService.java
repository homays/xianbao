package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CategoryService extends IService<Category> {

    void add(Category category);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Category selectById(Integer id);

    List<Category> selectAll(Category category);

    Page<Category> selectPage(Category category, Integer pageNum, Integer pageSize);
}
