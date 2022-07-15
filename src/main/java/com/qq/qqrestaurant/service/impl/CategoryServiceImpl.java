package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.CustomException;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Category;
import com.qq.qqrestaurant.entity.Dish;
import com.qq.qqrestaurant.mapper.CategoryMapper;
import com.qq.qqrestaurant.service.CategoryService;
import com.qq.qqrestaurant.service.DishService;
import com.qq.qqrestaurant.service.EmployeeService;
import com.qq.qqrestaurant.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;

    @Override
    public R<Page> page(int page, int pageSize) {
        Page<Category> pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        this.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @Override
    public R<String> addCategory(Category category) {
        if (this.save(category)) {
            return R.success("添加成功~");
        }
        return R.error("添加失败~");
    }

    @Override
    public R<String> editCategory(Category category) {
        if (this.updateById(category)) {
            return R.success("修改状态成功~");
        }
        return R.error("修改状态失败~");
    }

    @Override
    public R<String> delCategory(Long ids) {
        if(dishService.countCategory(ids)>0){
            throw new CustomException("当前分类已关联菜品，无法删除~");
        }

        if(setmealService.countCategory(ids)>0){
            throw new CustomException("当前分类已关联套餐，无法删除~");
        }

        if(this.removeById(ids)){
            return R.success("删除分类成功~");
        }
        return R.error("删除分类失败~");
    }

    @Override
    public R list(int type) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getType, type);
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        return R.success(this.list(queryWrapper));
    }
}
