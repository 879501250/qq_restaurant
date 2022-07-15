package com.qq.qqrestaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.qqrestaurant.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
