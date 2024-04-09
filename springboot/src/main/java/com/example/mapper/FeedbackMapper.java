package com.example.mapper;

import com.example.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    /**
     * 新增
     */
    int insert(Feedback feedback);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Feedback feedback);

    /**
     * 根据ID查询
     */
    Feedback selectById(Integer id);

    /**
     * 查询所有
     */
    List<Feedback> selectAll(Feedback feedback);

}




