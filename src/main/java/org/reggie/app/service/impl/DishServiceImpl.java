package org.reggie.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.reggie.app.dto.DishDto;
import org.reggie.app.entity.Dish;
import org.reggie.app.entity.DishFlavor;
import org.reggie.app.mapper.DishMapper;
import org.reggie.app.service.DishFlavorService;
import org.reggie.app.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        if(dishDto.getId() != null) {
            this.updateById(dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
            dishFlavorService.remove(queryWrapper);
        } else {
            this.save(dishDto);
        }
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach((item) -> item.setDishId(dishDto.getId()));
        dishFlavorService.saveBatch(flavors);
    }


    public List<DishFlavor> getDishFlavorById(Long id) {



        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        return dishFlavorService.list(queryWrapper);
    }

}
