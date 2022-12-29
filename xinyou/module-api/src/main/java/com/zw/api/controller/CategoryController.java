package com.zw.api.controller;

import com.zw.common.domain.Category;
import com.zw.common.utils.JsonResult;
import com.zw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //获取所有分类
    @Cacheable(value = "products", key = "'category'")
    @GetMapping("/list")
    public JsonResult<List<Category>> getAllCategory(){
        return new JsonResult(200,categoryService.list());
    }
    //添加分类
    @GetMapping("/save")
    public JsonResult<Boolean> saveCategory(Category category){
        return new JsonResult(200,categoryService.save(category));
    }
    //修改分类
    @GetMapping("/update")
    public JsonResult<Boolean> updateCategory(Category category){
        return new JsonResult(200,categoryService.updateById(category));
    }
    //删除分类
    @GetMapping("/delete/{id}")
    public JsonResult<Boolean> deleteCategory(Integer id){
        return new JsonResult(200,categoryService.removeById(id));
    }

    //根据id获取单个分类
    @GetMapping("/getOne/{id}")
    public JsonResult<Category> getOneCategory(Integer id){
        return new JsonResult(200,categoryService.getById(id));
    }

}
