package com.qq.qqrestaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qq.qqrestaurant.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
