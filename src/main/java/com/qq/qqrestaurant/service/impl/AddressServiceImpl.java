package com.qq.qqrestaurant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.qqrestaurant.common.BaseContext;
import com.qq.qqrestaurant.entity.AddressBook;
import com.qq.qqrestaurant.mapper.AddressMapper;
import com.qq.qqrestaurant.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressBook> implements AddressService {


    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        updateWrapper.set(AddressBook::getIsDefault, 0);
        this.update(updateWrapper);

        addressBook.setIsDefault(1);
        this.updateById(addressBook);
    }

    @Override
    public List<AddressBook> getList() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AddressBook::getIsDefault);
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        queryWrapper.eq(BaseContext.getCurrentId() != null, AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDeleted, 0);
        return this.list(queryWrapper);
    }

    @Override
    @Transactional
    public void delAddress(Long ids) {
        AddressBook addressBook = this.getById(ids);
        addressBook.setIsDeleted(1);
        if (addressBook.getIsDefault() == 1) {
            addressBook.setIsDefault(0);
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(AddressBook::getUpdateTime);
            queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
            List<AddressBook> addressBooks = this.list(queryWrapper);
            for (AddressBook address : addressBooks) {
                if (address.getId().longValue() != ids.longValue()) {
                    address.setIsDefault(1);
                    this.updateById(address);
                    break;
                }
            }
        }
        this.updateById(addressBook);
    }

    @Override
    public AddressBook getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        queryWrapper.eq(AddressBook::getIsDeleted, 0);
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        AddressBook addressBook = this.getOne(queryWrapper);
        if (addressBook != null) {
            return addressBook;
        }
        queryWrapper.clear();
        queryWrapper.eq(AddressBook::getIsDeleted, 0);
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBooks = this.list(queryWrapper);
        if (addressBooks.size() > 0) {
            return addressBooks.get(0);
        }
        return null;
    }

    @Override
    public void add(AddressBook addressBook) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        if (this.count(queryWrapper) == 0) {
            addressBook.setIsDefault(1);
        }
        addressBook.setUserId(BaseContext.getCurrentId());
        this.save(addressBook);
    }
}
