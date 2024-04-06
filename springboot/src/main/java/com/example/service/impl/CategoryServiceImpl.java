package com.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Category;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.CategoryService;
import com.example.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        Category dbCategory = getOne(Wrappers.<Category>lambdaQuery()
                .eq(Category::getName, category.getName()));
        if (ObjectUtil.isNotNull(dbCategory)) {
            throw new CustomException(ResultCodeEnum.CATEGORY_EXIST_ERROR);
        }
        categoryMapper.insert(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        categoryMapper.deleteBatchIds(ids);
    }

    @Override
    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    @Override
    public Page<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        Page<Category> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (category.getName() != null) {
            wrapper.eq(Category::getName, category.getName());
        }
        return categoryMapper.selectPage(page, wrapper);
    }
}




