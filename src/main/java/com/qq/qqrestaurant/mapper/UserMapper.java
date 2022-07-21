package com.qq.qqrestaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.qqrestaurant.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
