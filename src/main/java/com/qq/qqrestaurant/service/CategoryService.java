package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Category;

public interface CategoryService extends IService<Category> {

    R<Page> page(int page, int pageSize);

    R<String> addCategory(Category category);

    R<String> editCategory(Category category);

    R<String> delCategory(Long ids);

    R list(Category category);
}
