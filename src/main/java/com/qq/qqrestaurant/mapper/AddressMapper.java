package com.qq.qqrestaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.qqrestaurant.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<AddressBook> {
}
