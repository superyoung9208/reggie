package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.reggie.app.common.R;
import org.reggie.app.entity.Category;
import org.reggie.app.entity.Employee;
import org.reggie.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Category category){
        log.info("新增分类，分类信息：{}",category.toString());


        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");

        category.setCreateUser(empId);
        category.setUpdateUser(empId);

        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 根据id修改员工信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Category category){
        log.info(category.toString());

        Long empId = (Long)request.getSession().getAttribute("employee");

        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(empId);
        categoryService.updateById(category);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id修改员工信息
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(String id){

        categoryService.removeById(id);

        return R.success("删除分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Category::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Category::getUpdateTime);

        //执行查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/list")
    public R<List<Category>> list() {

        return R.success(categoryService.list());
    }

}
