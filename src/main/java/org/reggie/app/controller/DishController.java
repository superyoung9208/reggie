package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reggie.app.common.R;
import org.reggie.app.dto.DishDto;
import org.reggie.app.entity.Dish;
import org.reggie.app.entity.DishFlavor;
import org.reggie.app.service.DishFlavorService;
import org.reggie.app.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("更新成功");
    }

    @GetMapping("/{dishId}")
    public R<DishDto> getOne(@PathVariable("dishId") Long dishId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId, dishId);
        Dish dish = dishService.getOne(queryWrapper);
        List<DishFlavor> flavors = dishService.getDishFlavorById(dish.getId());
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(flavors);
        return R.success(dishDto);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(Dish::getIsDeleted, 0);

        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行查询
        dishService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/status/{status}")
    public R<String> releaseSell(@PathVariable("status") Integer status,Long[] ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> dishList =  dishService.list(queryWrapper);
        dishList.forEach(item -> {
            item.setStatus(status);
        });
        dishService.saveOrUpdateBatch(dishList);
        Long id = Thread.currentThread().getId();

        log.info("线程Id为：{}",id);
        return R.success((status == 1) ? "启售成功" : "停售成功");
    }

    @DeleteMapping
    public R<String> deleteDish(Long[] ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getStatus, 0);
        queryWrapper.in(Dish::getId, ids);
        dishService.count(queryWrapper);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> getDishListByCateId(Long categoryId) {

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        List<Dish> dishes = dishService.list(queryWrapper);
        log.info(dishes.toString());
        log.info(categoryId.toString());
        return R.success(dishes);
    }
}
