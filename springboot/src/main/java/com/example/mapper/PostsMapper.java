package com.example.mapper;

import com.example.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

    /**
     * 新增
     */
    int insert(Posts posts);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Posts posts);

    /**
     * 根据ID查询
     */
    Posts selectById(Integer id);

    /**
     * 查询所有
     */
    List<Posts> selectAll(Posts posts);

}




