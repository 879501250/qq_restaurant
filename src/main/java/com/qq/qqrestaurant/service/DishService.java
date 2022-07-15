package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.DishDto;
import com.qq.qqrestaurant.entity.Dish;

import java.util.ArrayList;
import java.util.List;

public interface DishService extends IService<Dish> {
    int countCategory(Long categoryId);

    R<Page> page(int page, int pageSize, String name);

    R getDish(Long id);

    R addDish(DishDto dishDto);

    R getList(Long categoryId);

    R update(DishDto dishDto);

    R updateDishStatus(Integer status, List<Long> ids);

    R delDish(ArrayList<Long> ids);
}
