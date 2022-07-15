package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService extends IService<SetmealDish> {

    List<SetmealDish> getBySetmealId(Long setmealId);

    Boolean removeBySetmealId(Long setmealId);
}
