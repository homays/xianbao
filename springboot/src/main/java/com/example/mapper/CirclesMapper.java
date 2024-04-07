package com.example.mapper;

import com.example.entity.Circles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CirclesMapper extends BaseMapper<Circles> {

    /**
     * 新增
     */
    int insert(Circles circles);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Circles circles);

    /**
     * 根据ID查询
     */
    Circles selectById(Integer id);

    /**
     * 查询所有
     */
    List<Circles> selectAll(Circles circles);

}




