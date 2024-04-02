package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reggie.app.common.R;
import org.reggie.app.entity.Dish;
import org.reggie.app.mapper.DishMapper;
import org.reggie.app.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    DishService dishService;

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
    public R<String> releaseSell(@PathVariable("status") Integer status,Long ids) {
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
}
