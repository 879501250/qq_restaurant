package com.qq.qqrestaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qq.qqrestaurant.common.BaseContext;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.AddressBook;
import com.qq.qqrestaurant.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping
    public R add(@RequestBody AddressBook addressBook) {
        addressService.add(addressBook);
        return R.success("保存成功~");
    }

    @GetMapping("/{id}")
    public R getAddress(@PathVariable Long id) {
        return R.success(addressService.getById(id));
    }

    @PutMapping("/default")
    public R setDefault(@RequestBody AddressBook addressBook) {
        addressService.setDefault(addressBook);
        return R.success("修改默认地址成功~");
    }

    @PutMapping
    public R update(@RequestBody AddressBook addressBook) {
        addressService.updateById(addressBook);
        return R.success("修改地址成功~");
    }

    @GetMapping("/list")
    public R getList() {
        return R.success(addressService.getList());
    }

    @DeleteMapping
    public R delAddress(Long ids) {
        addressService.delAddress(ids);
        return R.success("删除成功~");
    }

    @GetMapping("/default")
    public R getDefault() {
        return R.success(addressService.getDefault());
    }
}
