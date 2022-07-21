package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.SetmealDto;
import com.qq.qqrestaurant.entity.Dish;
import com.qq.qqrestaurant.entity.Setmeal;

import java.util.ArrayList;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    int countCategory(Long categoryId);

    R page(int page, int pageSize, String name);

    R addSetmeal(SetmealDto setmealDto);

    R getSetmeal(Long setmealId);

    R updateSetmeal(SetmealDto setmealDto);

    R updateSetmealStatus(Integer status, List<Long> ids);

    R delSetmeal(ArrayList<Long> ids);

    List<Setmeal> getByDishId(Long dishId);

    List<Setmeal> getList(Setmeal setmeal);

    Dish getDish(Long id);
}
