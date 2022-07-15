package com.qq.qqrestaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.DishDto;
import com.qq.qqrestaurant.service.DishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        return dishService.page(page,pageSize,name);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        return dishService.getDish(id);
    }

    @PostMapping
    public R save(@RequestBody DishDto dishDto){
        return dishService.addDish(dishDto);
    }

    @PutMapping
    public R update(@RequestBody DishDto dishDto){
        return dishService.update(dishDto);
    }

    @GetMapping("/list")
    public R getList(Long categoryId){
        return dishService.getList(categoryId);
    }

    @PostMapping("/status/{status}")
    public R updateDishStatus(@PathVariable Integer status,String ids) {
        String[] split = ids.split(",");
        ArrayList<Long> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Long.parseLong(split[i]));
        }
        return dishService.updateDishStatus(status, idList);
    }

    @DeleteMapping
    public R delDish(String ids) {
        String[] split = ids.split(",");
        ArrayList<Long> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Long.parseLong(split[i]));
        }
        return dishService.delDish(idList);
    }

}
