package org.reggie.app.dto;

import lombok.Data;
import org.reggie.app.entity.Dish;
import org.reggie.app.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;
}
