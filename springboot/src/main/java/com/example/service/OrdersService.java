package com.example.service;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.req.AddOrderDTO;
import com.example.vo.req.OrdersQueryDTO;

import java.util.List;


public interface OrdersService extends IService<Orders> {

    void add(AddOrderDTO addOrderDTO);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Orders selectById(Integer id);

    List<Orders> selectAll(Orders orders);


    Page<Orders> selectPage(Orders orders, OrdersQueryDTO ordersQueryDTO);

    Orders selectByOrderNo(String orderNo);

    List<Dict> selectLine();

    List<Dict> selectBar();
}
