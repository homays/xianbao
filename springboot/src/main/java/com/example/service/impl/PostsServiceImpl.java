package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Posts;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.PostsService;
import com.example.mapper.PostsMapper;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService{

    @Resource
    private PostsMapper postsMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Posts posts) {
        posts.setTime(DateUtil.now());
        String userId = ThreadLocalUtil.getUserId();
        posts.setUserId(Integer.parseInt(userId));
        posts.setStatus("待审核");
        postsMapper.insert(posts);
    }

    @Override
    public void deleteById(Integer id) {
        postsMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        postsMapper.deleteBatchIds(ids);
    }

    @Override
    public Posts selectById(Integer id) {
        return postsMapper.selectById(id);
    }

    @Override
    public List<Posts> selectAll(Posts posts) {
        return postsMapper.selectAll(posts);
    }

    @Override
    public Page<Posts> selectPage(Posts posts, Integer page, Integer size) {
        Page<Posts> postsPage = new Page<>(page, size);
        LambdaQueryWrapper<Posts> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(posts.getTitle()), Posts::getTitle, posts.getTitle());
        Page<Posts> result = postsMapper.selectPage(postsPage, wrapper);
        List<Posts> records = result.getRecords();
        List<Posts> collect = records.stream().peek(item -> {
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getId, item.getUserId()));
            item.setUserName(user.getName());
        }).collect(Collectors.toList());
        result.setRecords(collect);
        return result;
    }
}




