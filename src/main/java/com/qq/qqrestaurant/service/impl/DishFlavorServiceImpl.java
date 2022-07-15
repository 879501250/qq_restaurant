package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.entity.DishFlavor;
import com.qq.qqrestaurant.mapper.DishFlavorMapper;
import com.qq.qqrestaurant.service.DishFlavorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

    @Override
    public List<DishFlavor> getByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishId);
        queryWrapper.eq(DishFlavor::getIsDeleted, 0);
        return this.list(queryWrapper);
    }

    @Override
    public Boolean removeByDishId(Long dishID) {
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishID);
        return this.remove(queryWrapper);
    }
}
