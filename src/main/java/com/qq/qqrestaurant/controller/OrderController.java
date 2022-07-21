package com.qq.qqrestaurant.controller;

import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Orders;
import com.qq.qqrestaurant.service.OrderService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/submit")
    public R submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功~");
    }

    @GetMapping("/userPage")
    public R userPage(Integer page,Integer pageSize){
        return R.success(orderService.userPage(page,pageSize));
    }
}
