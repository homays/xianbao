package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Notice;

import java.util.List;

/**
 * 公告信息表业务处理
 **/
public interface NoticeService extends IService<Notice> {

    void add(Notice notice);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateNotice(Notice notice);

    Notice selectById(Integer id);

    List<Notice> selectAll(Notice notice);

    Page<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);

}