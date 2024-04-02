package org.reggie.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.reggie.app.entity.Category;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
