package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Circles;
import com.example.service.CirclesService;
import com.example.mapper.CirclesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CirclesServiceImpl extends ServiceImpl<CirclesMapper, Circles> implements CirclesService{

    @Resource
    private CirclesMapper circlesMapper;

    @Override
    public void add(Circles circles) {
        circlesMapper.insert(circles);
    }

    @Override
    public void deleteById(Integer id) {
        circlesMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        circlesMapper.deleteBatchIds(ids);
    }

    @Override
    public Circles selectById(Integer id) {
        return circlesMapper.selectById(id);
    }

    @Override
    public List<Circles> selectAll(Circles circles) {
        return circlesMapper.selectAll(circles);
    }

    @Override
    public Page<Circles> selectPage(Circles circles, Integer pageNum, Integer pageSize) {
        Page<Circles> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Circles> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(circles.getName()), Circles::getName, circles.getName());
        return circlesMapper.selectPage(page, wrapper);
    }
}




