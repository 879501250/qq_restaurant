package com.qq.qqrestaurant.controller;

import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.ShoppingCart;
import com.qq.qqrestaurant.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R list() {
        return R.success(shoppingCartService.getList());
    }

    @PostMapping("/add")
    public R add(@RequestBody ShoppingCart shoppingCart){
        shoppingCartService.add(shoppingCart);
        return R.success("添加成功~");
    }

    @PostMapping("/sub")
    public R sub(@RequestBody ShoppingCart shoppingCart){
        shoppingCartService.sub(shoppingCart);
        return R.success("删除成功~");
    }

    @DeleteMapping("/clean")
    public R clean(){
        shoppingCartService.clean();
        return R.success("删除成功~");
    }
}
