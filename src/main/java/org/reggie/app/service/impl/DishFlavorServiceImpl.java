package org.reggie.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.reggie.app.entity.DishFlavor;
import org.reggie.app.mapper.DishFlavorMapper;
import org.reggie.app.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
