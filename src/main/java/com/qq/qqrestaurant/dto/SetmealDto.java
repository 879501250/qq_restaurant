package com.qq.qqrestaurant.dto;

import com.qq.qqrestaurant.entity.Setmeal;
import com.qq.qqrestaurant.entity.SetmealDish;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SetmealDto extends Setmeal implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
