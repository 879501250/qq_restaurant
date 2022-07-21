package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.entity.User;

public interface UserService extends IService<User> {
    User login(String phone);
}
