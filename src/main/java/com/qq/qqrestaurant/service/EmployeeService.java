package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> logout(HttpServletRequest request);

    R<Page> page(int page, int pageSize, String name);

    R<Employee> queryEmployeeById(Long id);

    R<String> addEmployee(HttpServletRequest request, Employee employee);

    R<String> editEmployee(HttpServletRequest request, Employee employee);
}
