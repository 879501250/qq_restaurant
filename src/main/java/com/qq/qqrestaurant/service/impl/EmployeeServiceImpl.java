package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.Employee;
import com.qq.qqrestaurant.mapper.EmployeeMapper;
import com.qq.qqrestaurant.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = this.getOne(wrapper);

        if (emp == null) {
            return R.error("账号不存在~");
        }

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误~");
        }

        if (emp.getStatus() == 0) {
            return R.error("账号已禁用~");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出登录成功~");
    }

    @Override
    public R<Page> page(int page, int pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        this.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @Override
    public R<Employee> queryEmployeeById(Long id) {
        if (id == null) {
            return R.error("你很坏哦~");
        }
        Employee employee = this.getById(id);
        if (employee == null) {
            return R.error("id不存在~");
        }
        return R.success(employee);
    }

    @Override
    public R<String> addEmployee(HttpServletRequest request, Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        if (this.save(employee)) {
            return R.success("添加成功~");
        }
        return R.error("添加失败~");
    }

    @Override
    public R<String> editEmployee(HttpServletRequest request, Employee employee) {
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateTime(LocalDateTime.now());
        if (this.updateById(employee)) {
            return R.success("修改状态成功~");
        }
        return R.error("修改状态失败~");
    }
}
