package com.qq.qqrestaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.qqrestaurant.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
