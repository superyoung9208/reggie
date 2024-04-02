package org.reggie.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.reggie.app.dto.DishDto;
import org.reggie.app.entity.Dish;
import org.reggie.app.entity.DishFlavor;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    List<DishFlavor> getDishFlavorById(Long id);

}
