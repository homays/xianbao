package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Circles;
import com.example.entity.Posts;
import com.example.entity.User;
import com.example.mapper.CirclesMapper;
import com.example.mapper.UserMapper;
import com.example.service.PostsService;
import com.example.mapper.PostsMapper;
import com.example.utils.RedisUtil;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.PostsQueryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.common.Constants.XIANBAO_POSTS_READCOUNT;

@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService{

    @Resource
    private PostsMapper postsMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CirclesMapper circlesMapper;
    @Resource
    private RedisUtil redisUtil;

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
        Posts posts = postsMapper.selectById(id);
        String userId = ThreadLocalUtil.getUserId();
        User user = userMapper.selectById(Integer.parseInt(userId));
        posts.setUserName(user.getName());
        Long readCount = redisUtil.incr(XIANBAO_POSTS_READCOUNT + id);
        posts.setReadCount(readCount.intValue());
        return posts;
    }

    @Override
    public List<Posts> selectAll(Posts posts) {
        return postsMapper.selectAll(posts);
    }

    @Override
    public Page<Posts> selectPage(Posts posts, Integer page, Integer size) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String role = claims.get("role").toString();
        if (RoleEnum.USER.name().equals(role)) {
            posts.setUserId(Integer.parseInt(claims.get("userId").toString()));
        }
        Page<Posts> postsPage = new Page<>(page, size);
        LambdaQueryWrapper<Posts> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(posts.getUserId() != null, Posts::getUserId, posts.getUserId());
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

    @Override
    public Page<Posts> selectFrontPage(Posts posts, PostsQueryDTO postsQueryDTO) {
        Page<Posts> postsPage = new Page<>(postsQueryDTO.getPageNum(), postsQueryDTO.getPageSize());
        LambdaQueryWrapper<Posts> wrapper = new LambdaQueryWrapper<>();
        String circle = postsQueryDTO.getCircle();
        if (StrUtil.isNotBlank(circle) && Objects.equals(circle, "全部")) {
            circle = null;
        }
        if (circle != null) {
            Circles circles = circlesMapper.selectOne(Wrappers.<Circles>lambdaQuery()
                    .eq(Circles::getName, circle));
            wrapper.eq(Posts::getCircle, circles.getId());
        }
        wrapper.eq(Posts::getStatus, StatusEnum.APPROVE.value);
        wrapper.orderByDesc(Posts::getTime);
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




