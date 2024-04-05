package org.reggie.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reggie.app.dto.SetmealDto;
import org.reggie.app.entity.Setmeal;
import org.reggie.app.entity.SetmealDish;
import org.reggie.app.mapper.SetmealMapper;
import org.reggie.app.service.CategoryService;
import org.reggie.app.service.SetmealDishService;
import org.reggie.app.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public void saveWithDishs(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(item -> item.setSetmealId(setmealDto.getId()));
        setmealDishService.saveBatch(setmealDishes);
    }

    @Transactional
    /*更新套餐*/
    public void updateWithDishs(SetmealDto setmealDto) {
        this.updateById(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        setmealDishes.forEach(item -> item.setSetmealId(setmealDto.getId()));
        setmealDishService.saveBatch(setmealDishes);
        setmealDishService.updateBatchById(setmealDishes);
    }

    @Transactional
    public void deleteWithDishs(List<Long> ids) {
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId, (Object) ids);
        setmealDishService.remove(queryWrapper);
    }

    @Override
    public String getSetmealCategoryNameById(Long cateId) {
        return categoryService.getById(cateId).getName();
    }

}
