package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.OrderStatusEnum;
import com.example.entity.Address;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.entity.User;
import com.example.mapper.AddressMapper;
import com.example.mapper.GoodsMapper;
import com.example.mapper.UserMapper;
import com.example.service.AddressService;
import com.example.service.GoodsService;
import com.example.service.OrdersService;
import com.example.mapper.OrdersMapper;
import com.example.service.UserService;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.AddOrderDTO;
import com.example.vo.req.OrdersQueryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(AddOrderDTO addOrderDTO) {
        String userId = ThreadLocalUtil.getUserId();
        User user = userMapper.selectById(Integer.parseInt(userId));
        Integer addressId = addOrderDTO.getAddressId();
        Address address = addressMapper.selectById(addressId);
        Integer goodsId = addOrderDTO.getGoodsId();
        Goods goods = goodsMapper.selectById(goodsId);
        Integer saleId = goods.getUserId();
        User sale = userMapper.selectById(saleId);

        Orders orders = new Orders();
        orders.setOrderNo(generateOrderId(user.getId()));
        orders.setStatus(OrderStatusEnum.UNPAID.value);
        orders.setGoodsName(goods.getName());
        orders.setGoodsImg(goods.getImg());
        orders.setTotal(goods.getPrice());
        orders.setTime(DateUtil.now());
        orders.setUserId(user.getId());
        orders.setUserName(user.getName());
        orders.setAddress(address.getAddress());
        orders.setPhone(address.getPhone());
        orders.setSaleId(saleId);
        orders.setSaleName(sale.getName());

        ordersMapper.insert(orders);
    }

    public String generateOrderId(int userId) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        String datePart = dtf.format(now);
        String timePart = String.valueOf(System.currentTimeMillis());
        return datePart + userId + timePart;
    }

    @Override
    public void deleteById(Integer id) {
        ordersMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        ordersMapper.deleteBatchIds(ids);
    }

    @Override
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    @Override
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectList(null);
    }

    @Override
    public Page<Orders> selectPage(Orders orders, OrdersQueryDTO ordersQueryDTO) {
        String orderNo = ordersQueryDTO.getOrderNo();
        String goodsName = ordersQueryDTO.getGoodsName();
        String orderStatus = ordersQueryDTO.getOrderStatus();
        Page<Orders> page = new Page<>(ordersQueryDTO.getPageNum(), ordersQueryDTO.getPageSize());
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(orderNo), Orders::getOrderNo, orderNo);
        wrapper.like(StrUtil.isNotBlank(goodsName), Orders::getGoodsName, goodsName);
        wrapper.eq(StrUtil.isNotBlank(orderStatus), Orders::getStatus, orderStatus);
        Page<Orders> ordersPage = ordersMapper.selectPage(page, wrapper);
        List<Orders> collect = ordersPage.getRecords().stream().map(item -> {
            User user = userMapper.selectById(item.getSaleId());
            item.setSaleName(user.getName());
            return item;
        }).collect(Collectors.toList());
        ordersPage.setRecords(collect);
        return ordersPage;
    }
}




