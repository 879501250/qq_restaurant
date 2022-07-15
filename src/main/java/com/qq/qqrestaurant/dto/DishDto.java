package com.qq.qqrestaurant.dto;

import com.qq.qqrestaurant.entity.Dish;
import com.qq.qqrestaurant.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
