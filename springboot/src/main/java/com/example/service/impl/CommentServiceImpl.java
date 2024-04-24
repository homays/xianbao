package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Comment;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.CommentService;
import com.example.mapper.CommentMapper;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.CommentChildrenVO;
import com.example.vo.req.CommentVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Comment comment) {
        String userId = ThreadLocalUtil.getUserId();
        comment.setUserId(Integer.valueOf(userId));
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);

        if (comment.getPid() == null) {
            comment.setRootId(comment.getId());
        } else {
            Comment parentComment = commentMapper.selectOne(Wrappers.<Comment>lambdaQuery()
                    .eq(Comment::getId, comment.getPid()));
            comment.setRootId(parentComment.getRootId());
        }
        this.updateById(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    @Override
    public void deleteDeep(Integer id) {
        Comment comment = commentMapper.selectOne(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getPid, id));
        if (ObjectUtil.isNull(comment)) {
            return;
        }
        commentMapper.deleteById(id);
        this.deleteDeep(comment.getId());
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        commentMapper.deleteBatchIds(ids);
    }

    @Override
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(null);
    }

    @Override
    public Page<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        Page<Comment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(comment.getContent()), Comment::getContent, comment.getContent());
        Page<Comment> commentPage = commentMapper.selectPage(page, wrapper);
        List<Comment> records = commentPage.getRecords();
        List<Comment> collect = records.stream().peek(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getUsername());
        }).collect(Collectors.toList());
        commentPage.setRecords(collect);
        return commentPage;
    }

    @Override
    public List<CommentVO> selectTree(Integer fid, String module) {
        // 1、查询所有评论
        List<Comment> commentsList = commentMapper.selectList(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getFid, fid)
                .eq(Comment::getModule, module));
        // 2、获取所有一级节点评论 (pid == null)
        List<Comment> rootList = commentsList.stream().filter(item -> item.getPid() == null).collect(Collectors.toList());
        return rootList.stream().map(item -> {
            User user = userMapper.selectById(item.getUserId());
            CommentVO commentVO = new CommentVO();
            BeanUtil.copyProperties(item, commentVO);
            commentVO.setUserName(user.getName());
            commentVO.setUserImg(user.getAvatar());
            // 3、获取所有子节点评论
            List<Comment> childComment = commentsList.stream()
                    .filter(child -> Objects.equals(child.getRootId(), item.getRootId()) && child.getPid() != null).collect(Collectors.toList());

            List<CommentChildrenVO> collect = childComment.stream().map(children -> {
                // 获取父节点的用户信息
                Comment comment = commentMapper.selectOne(Wrappers.<Comment>lambdaQuery()
                        .eq(Comment::getId, children.getPid()));
                Integer userId = comment.getUserId();
                User parentUser = userMapper.selectById(userId);
                User childrenUser = userMapper.selectById(children.getUserId());
                CommentChildrenVO childrenVO = new CommentChildrenVO();
                BeanUtil.copyProperties(children, childrenVO);
                childrenVO.setUserName(childrenUser.getName());
                childrenVO.setUserImg(childrenUser.getAvatar());
                childrenVO.setParentName(parentUser.getName());
                childrenVO.setParentImg(parentUser.getAvatar());
                return childrenVO;
            }).collect(Collectors.toList());
            commentVO.setChrildrenList(collect);
            return commentVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer selectCount(Integer fid, String module) {
        return Math.toIntExact(commentMapper.selectCount(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getFid, fid)
                .eq(Comment::getModule, module)));
    }
}




