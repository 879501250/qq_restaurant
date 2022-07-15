package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.SetmealDto;
import com.qq.qqrestaurant.entity.Category;
import com.qq.qqrestaurant.entity.Setmeal;
import com.qq.qqrestaurant.entity.SetmealDish;
import com.qq.qqrestaurant.mapper.SetmealMapper;
import com.qq.qqrestaurant.service.CategoryService;
import com.qq.qqrestaurant.service.SetmealDishService;
import com.qq.qqrestaurant.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealDishService setmealDishService;
    @Resource
    private CategoryService categoryService;

    /**
     * 查询某一分类的套餐数量
     *
     * @param categoryId
     * @return
     */
    @Override
    public int countCategory(Long categoryId) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, categoryId);
        return this.count(queryWrapper);
    }

    @Override
    public R page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.eq(Setmeal::getIsDeleted, 0);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        this.page(setmealPage, queryWrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<SetmealDto> setmealDtos = setmealPage.getRecords().stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Category category = categoryService.getById(setmeal.getCategoryId());
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtos);

        return R.success(setmealDtoPage);
    }

    @Override
    public R addSetmeal(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        Collection<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
        setmealDishService.saveBatch(setmealDishes);
        return R.success("保存成功~");
    }

    @Override
    public R getSetmeal(Long setmealId) {
        Setmeal setmeal = this.getById(setmealId);
        if (setmeal != null) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);

            setmealDto.setSetmealDishes(setmealDishService.getBySetmealId(setmeal.getId()));

            setmealDto.setCategoryName(categoryService.getById(setmeal.getCategoryId()).getName());

            return R.success(setmealDto);
        }
        return R.error("该套餐不存在~");
    }

    @Override
    public R updateSetmeal(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        setmealDishService.removeBySetmealId(setmealDto.getId());

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealDto.getId());
            return setmealDish;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);

        return R.success("修改套餐成功~");
    }

    @Override
    public R updateSetmealStatus(Integer status, List<Long> ids) {
        Setmeal setmeal;
        for (Long id : ids) {
            setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setStatus(status);
            this.updateById(setmeal);
        }
        return R.success(status == 1 ? "启售" : "停售" + "套餐成功~");
    }

    @Override
    public R delSetmeal(ArrayList<Long> ids) {
        Setmeal setmeal;
        for (Long id : ids) {
            setmeal = new Setmeal();
            setmeal.setId(id);
            setmeal.setIsDeleted(1);
            this.updateById(setmeal);
        }
        return R.success("删除套餐成功~");
    }
}
