package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Orders;
import com.qq.qqrestaurant.mapper.OrderMapper;
import com.qq.qqrestaurant.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Override
    public R page(Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        queryWrapper.like(number != null, Orders::getNumber, number);
        queryWrapper.between(beginTime != null && endTime != null, Orders::getOrderTime, beginTime, endTime);
        this.page(ordersPage, queryWrapper);
        return R.success(ordersPage);
    }
}
