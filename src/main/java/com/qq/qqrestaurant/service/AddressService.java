package com.qq.qqrestaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.qqrestaurant.entity.AddressBook;

import java.util.List;

public interface AddressService extends IService<AddressBook> {
    void setDefault(AddressBook addressBook);

    List<AddressBook> getList();

    void delAddress(Long ids);

    AddressBook getDefault();

    void add(AddressBook addressBook);
}
