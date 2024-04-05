package org.reggie.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.reggie.app.dto.SetmealDto;
import org.reggie.app.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDishs(SetmealDto setmealDto);

    void updateWithDishs(SetmealDto setmealDto);

    void deleteWithDishs(List<Long> ids);

    String getSetmealCategoryNameById(Long id);
}
