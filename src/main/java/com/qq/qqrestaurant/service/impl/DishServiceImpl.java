package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.DishDto;
import com.qq.qqrestaurant.entity.Category;
import com.qq.qqrestaurant.entity.Dish;
import com.qq.qqrestaurant.entity.DishFlavor;
import com.qq.qqrestaurant.mapper.DishMapper;
import com.qq.qqrestaurant.service.CategoryService;
import com.qq.qqrestaurant.service.DishFlavorService;
import com.qq.qqrestaurant.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private CategoryService categoryService;

    /**
     * 查询某一分类的菜品数量
     *
     * @param categoryId
     * @return
     */
    @Override
    public int countCategory(Long categoryId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        return this.count(queryWrapper);
    }

    @Override
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        if (name != null) {
//            queryWrapper.like(Dish::getName, name);
//        }
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByAsc(Dish::getSort);
        queryWrapper.eq(Dish::getIsDeleted, 0);
        this.page(dishPage, queryWrapper);

        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        List<DishDto> dishDtos = dishPage.getRecords().stream().map(dish -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            Category category = categoryService.getById(dish.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtos);
        return R.success(dishDtoPage);
    }

    @Override
    public R addDish(DishDto dishDto) {
        this.save(dishDto);
        // 设置菜品口味关联的菜品id
        dishDto.getFlavors().forEach(dishFlavor -> dishFlavor.setDishId(dishDto.getId()));
        dishFlavorService.saveBatch(dishDto.getFlavors());
        return R.success("保存成功~");
    }

    @Override
    public R getList(Long categoryId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        queryWrapper.eq(Dish::getIsDeleted, 0);
        List<Dish> dishes = this.list(queryWrapper);

        return R.success(dishes);
    }

    @Override
    public R update(DishDto dishDto) {
        this.updateById(dishDto);
        dishFlavorService.removeByDishId(dishDto.getId());
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors = dishFlavors.stream().map(dishFlavor -> {
            dishFlavor.setDishId(dishDto.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(dishFlavors);
        return R.success("修改菜品成功~");
    }

    @Override
    public R updateDishStatus(Integer status, List<Long> ids) {
        Dish dish;
        for (Long id : ids) {
            dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            this.updateById(dish);
        }
        return R.success(status == 1 ? "启售" : "停售" + "菜品成功~");
    }

    @Override
    public R delDish(ArrayList<Long> ids) {
        Dish dish;
        for (Long id : ids) {
            dish = new Dish();
            dish.setId(id);
            dish.setIsDeleted(1);
            this.updateById(dish);
        }
        return R.success("删除菜品成功~");
    }

    @Override
    public R getDish(Long id) {
        Dish dish = this.getById(id);
        if (dish != null) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(this.getById(id), dishDto);

            List<DishFlavor> flavors = dishFlavorService.getByDishId(id);
            dishDto.setFlavors(flavors);

            Category category = categoryService.getById(dish.getCategoryId());
            dishDto.setCategoryName(category.getName());

            return R.success(dishDto);
        } else {
            return R.error("不存在该菜品~");
        }
    }
}
