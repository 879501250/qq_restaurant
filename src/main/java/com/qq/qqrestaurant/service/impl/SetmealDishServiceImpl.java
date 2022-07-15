package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.entity.SetmealDish;
import com.qq.qqrestaurant.mapper.SetmealDishMapper;
import com.qq.qqrestaurant.mapper.SetmealMapper;
import com.qq.qqrestaurant.service.SetmealDishService;
import com.qq.qqrestaurant.service.SetmealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
    @Override
    public List<SetmealDish> getBySetmealId(Long setmealId) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getIsDeleted, 0);
        queryWrapper.orderByDesc(SetmealDish::getSort);
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        return this.list(queryWrapper);
    }

    @Override
    public Boolean removeBySetmealId(Long setmealId) {
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealId);
        return this.remove(queryWrapper);
    }
}
