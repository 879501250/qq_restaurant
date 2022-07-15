package com.qq.qqrestaurant.controller;

import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/page")
    public R page(Integer page, Integer pageSize, String number, LocalDateTime beginTime, LocalDateTime endTime) {
        return R.success(orderService.page(page, pageSize, number, beginTime, endTime));
    }
}
