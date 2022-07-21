package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.entity.OrderDetail;
import com.qq.qqrestaurant.mapper.OrderDetailMapper;
import com.qq.qqrestaurant.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
