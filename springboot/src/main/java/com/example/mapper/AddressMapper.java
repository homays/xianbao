package com.example.mapper;

import com.example.entity.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {

    /**
     * 新增
     */
    int insert(Address address);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Address address);

    /**
     * 根据ID查询
     */
    Address selectById(Integer id);

    /**
     * 查询所有
     */
    List<Address> selectAll(Address address);

}




