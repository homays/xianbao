package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Feedback;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.FeedbackService;
import com.example.mapper.FeedbackMapper;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Feedback feedback) {
        String userId = ThreadLocalUtil.getUserId();
        feedback.setUserId(Integer.parseInt(userId));
        feedback.setCreatetime(DateUtil.now());
        feedbackMapper.insert(feedback);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        feedbackMapper.deleteBatchIds(ids);
    }

    @Override
    public Feedback selectById(Integer id) {
        return feedbackMapper.selectById(id);
    }

    @Override
    public List<Feedback> selectAll(Feedback feedback) {
        return feedbackMapper.selectList(null);
    }

    @Override
    public Page<Feedback> selectPage(Feedback feedback, Integer pageNum, Integer pageSize) {
        Page<Feedback> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(feedback.getTitle()), Feedback::getTitle, feedback.getTitle());
        Page<Feedback> feedbackPage = feedbackMapper.selectPage(page, wrapper);
        List<Feedback> records = feedbackPage.getRecords();
        List<Feedback> collect = records.stream().peek(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getName());
        }).collect(Collectors.toList());
        feedbackPage.setRecords(collect);
        return feedbackPage;
    }
}




