package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.BaseContext;
import com.qq.qqrestaurant.entity.ShoppingCart;
import com.qq.qqrestaurant.mapper.ShoppingCartMapper;
import com.qq.qqrestaurant.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public void add(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cart = this.getOne(queryWrapper);
        if (cart != null) {
            cart.setNumber(cart.getNumber() + 1);
            this.updateById(cart);
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setUserId(BaseContext.getCurrentId());
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
        }
    }

    @Override
    public void sub(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            shoppingCart = this.getOne(queryWrapper);
            if (shoppingCart != null) {
                if (shoppingCart.getNumber() > 1) {
                    shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                    this.updateById(shoppingCart);
                } else {
                    this.removeById(shoppingCart);
                }
            }
        }
    }

    @Override
    public List<ShoppingCart> getList() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        return this.list(queryWrapper);
    }

    @Override
    public void clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        this.remove(queryWrapper);
    }
}
