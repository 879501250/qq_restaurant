package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Orders;

import java.time.LocalDateTime;

public interface OrderService extends IService<Orders> {
    R page(Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime);
}
