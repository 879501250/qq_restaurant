package com.qq.qqrestaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Category;
import com.qq.qqrestaurant.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        return categoryService.page(page, pageSize);
    }

    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping
    public R<String> editCategory(@RequestBody Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping
    public R<String> delCategory(Long ids){
        return categoryService.delCategory(ids);
    }

    @GetMapping("/list")
    public R list(int type) {
        return categoryService.list(type);
    }

}
