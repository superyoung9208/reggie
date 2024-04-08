package org.reggie.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.reggie.app.common.R;
import org.reggie.app.entity.ShoppingCart;
import org.reggie.app.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @RequestMapping("/list")
    public R<List<ShoppingCart>> list(){

        shoppingCartService.list();

        List<ShoppingCart> shoppingCarts = shoppingCartService.list();

        return R.success(shoppingCarts);
    }

}
