package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reggie.app.common.R;
import org.reggie.app.dto.SetmealDto;
import org.reggie.app.entity.Setmeal;
import org.reggie.app.entity.SetmealDish;
import org.reggie.app.service.SetmealDishService;
import org.reggie.app.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    private final SetmealDishService setmealDishService;


    @Autowired
    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
    }


    /**
     * 分页查询套餐信息
     *
     * @param page     请求的页码
     * @param pageSize 每页显示的数量
     * @param name     套餐名称，支持模糊查询
     * @return R<Page> 返回一个包含分页信息的结果对象
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        // 初始化分页信息
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);

        Page<SetmealDto> dtoPage = new Page<>();

        // 创建查询条件
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        // 根据套餐名称添加模糊查询条件，如果名称不为空
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        // 按更新时间倒序排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        // 执行分页查询
        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage, "records");

        List<SetmealDto> records = pageInfo.getRecords().stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            if(item.getCategoryId() != null) {
                setmealDto.setCategoryName(setmealService.getSetmealCategoryNameById(item.getCategoryId()));
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(records);

        // 返回查询结果
        return R.success(dtoPage);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getOne(@PathVariable("id") Long id) {
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        return R.success(setmealDto);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        LambdaQueryWrapper<Setmeal> countQueryWrapper = new LambdaQueryWrapper<>();
        countQueryWrapper.in(Setmeal::getId, ids);
        countQueryWrapper.eq(Setmeal::getStatus, 1);
        if (setmealService.count(countQueryWrapper) > 0) {
            return R.error("套餐正在售卖中，不能删除");
        }

        setmealService.deleteWithDishs(ids);
        return R.success("删除成功");
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {

        log.info(setmealDto.toString());
        setmealService.saveWithDishs(setmealDto);
        return R.success("保存成功");
    }

    /*更新套餐信息*/
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        setmealService.updateWithDishs(setmealDto);
        return R.success("更新成功");
    }

    @PostMapping("/status/{status}")
    public R<String> releaseSell(@PathVariable("status") Integer status,Long[] ids) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(Setmeal::getId, (Object) ids);

        List<Setmeal> setmeals = setmealService.list(queryWrapper);

        setmeals.forEach(item -> item.setStatus(status));

        setmealService.updateBatchById(setmeals);

        return R.success("更新成功");
    }

}
