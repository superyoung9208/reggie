package org.reggie.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.reggie.app.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
