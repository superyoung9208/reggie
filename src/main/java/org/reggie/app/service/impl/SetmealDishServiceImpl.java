package org.reggie.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reggie.app.entity.SetmealDish;
import org.reggie.app.mapper.SetmealDishMapper;
import org.reggie.app.service.SetmealDishService;
import org.springframework.stereotype.Service;


@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
