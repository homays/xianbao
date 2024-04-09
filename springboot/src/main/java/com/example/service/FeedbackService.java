package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FeedbackService extends IService<Feedback> {

    void add(Feedback feedback);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Feedback selectById(Integer id);

    List<Feedback> selectAll(Feedback feedback);

    Page<Feedback> selectPage(Feedback feedback, Integer pageNum, Integer pageSize);
}
