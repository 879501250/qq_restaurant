package com.qq.qqrestaurant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Employee;
import com.qq.qqrestaurant.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.login(request, employee);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        return employeeService.page(page, pageSize, name);
    }

    @GetMapping("/{id}")
    public R<Employee> queryEmployeeById(@PathVariable Long id) {
        return employeeService.queryEmployeeById(id);
    }

    @PostMapping
    public R<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息：{}", employee.toString());
        return employeeService.addEmployee(request, employee);
    }

    @PutMapping
    public R<String> editEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.editEmployee(request, employee);
    }
}
