package com.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Circles;
import com.example.entity.Posts;
import com.example.exception.CustomException;
import com.example.mapper.PostsMapper;
import com.example.service.CirclesService;
import com.example.mapper.CirclesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CirclesServiceImpl extends ServiceImpl<CirclesMapper, Circles> implements CirclesService{

    @Resource
    private CirclesMapper circlesMapper;
    @Resource
    private PostsMapper postsMapper;

    @Override
    public void add(Circles circles) {
        Circles dbCircles = getOne(Wrappers.<Circles>lambdaQuery()
                .eq(Circles::getName, circles.getName()));
        if (ObjectUtil.isNotNull(dbCircles)) {
            throw new CustomException(ResultCodeEnum.CIRCLES_EXIST_ERROR);
        }
        circlesMapper.insert(circles);
    }

    @Override
    public void deleteById(Integer id) {
        Circles circles = selectById(id);
        List<Posts> posts = postsMapper.selectList(null);
        for (Posts post : posts) {
            if (post.getCircle().equals(circles.getName())) {
                throw new CustomException(ResultCodeEnum.CIRCLES_NOT_DELETE_ERROR);
            }
        }
        circlesMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        List<Posts> posts = postsMapper.selectList(null);
        for (Posts post : posts) {
            for (Integer id : ids) {
                Circles circles = selectById(id);
                if (post.getCircle().equals(circles.getName())) {
                    throw new CustomException(ResultCodeEnum.CIRCLES_NOT_DELETE_ERROR);
                }
            }
        }
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




