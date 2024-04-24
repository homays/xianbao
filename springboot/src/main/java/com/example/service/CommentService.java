package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.req.CommentVO;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void add(Comment comment);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Comment selectById(Integer id);

    List<Comment> selectAll(Comment comment);

    Page<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize);

    List<CommentVO> selectTree(Integer fid, String module);

    Integer selectCount(Integer fid, String module);

    void deleteDeep(Integer id);
}
