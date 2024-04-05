package org.reggie.app.dto;

import lombok.Data;
import org.reggie.app.entity.Setmeal;
import org.reggie.app.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
