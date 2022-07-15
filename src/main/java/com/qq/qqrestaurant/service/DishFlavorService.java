package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService extends IService<DishFlavor> {
    List<DishFlavor> getByDishId(Long dishId);

    Boolean removeByDishId(Long dishID);
}
