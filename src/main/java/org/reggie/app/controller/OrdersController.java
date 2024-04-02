package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reggie.app.common.R;
import org.reggie.app.entity.Orders;
import org.reggie.app.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number) {
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        queryWrapper.eq(Orders::getStatus, 1);

        queryWrapper.orderByDesc(Orders::getOrderTime);

        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
