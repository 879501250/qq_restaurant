package com.qq.qqrestaurant.controller;

import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.dto.SetmealDto;
import com.qq.qqrestaurant.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Resource
    private SetmealService setmealService;

    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
        return setmealService.page(page, pageSize, name);
    }

    @PostMapping
    public R addSetmeal(@RequestBody SetmealDto setmealDto) {
        return setmealService.addSetmeal(setmealDto);
    }

    @PutMapping
    public R updateSetmeal(@RequestBody SetmealDto setmealDto) {
        return setmealService.updateSetmeal(setmealDto);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        return setmealService.getSetmeal(id);
    }

    @PostMapping("/status/{status}")
    public R updateSetmealStatus(@PathVariable Integer status,String ids){
        String[] split = ids.split(",");
        ArrayList<Long> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Long.parseLong(split[i]));
        }
        return setmealService.updateSetmealStatus(status,idList);
    }

    @DeleteMapping
    public R delSetmeal(String ids){
        String[] split = ids.split(",");
        ArrayList<Long> idList = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            idList.add(Long.parseLong(split[i]));
        }
        return setmealService.delSetmeal(idList);
    }
}
