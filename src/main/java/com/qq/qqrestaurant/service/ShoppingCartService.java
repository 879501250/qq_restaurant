package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    void add(ShoppingCart shoppingCart);

    List<ShoppingCart> getList();

    void clean();

    void sub(ShoppingCart shoppingCart);
}
