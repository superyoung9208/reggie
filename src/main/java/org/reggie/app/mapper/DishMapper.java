package org.reggie.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.reggie.app.entity.Dish;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
