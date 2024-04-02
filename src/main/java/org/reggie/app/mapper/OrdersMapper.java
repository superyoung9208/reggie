package org.reggie.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.reggie.app.entity.Orders;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
