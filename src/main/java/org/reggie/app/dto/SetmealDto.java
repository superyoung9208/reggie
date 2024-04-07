package org.reggie.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.reggie.app.entity.Setmeal;
import org.reggie.app.entity.SetmealDish;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
