package com.zw.backstage.controller;

import com.google.gson.JsonObject;
import com.zw.common.domain.Category;
import com.zw.common.domain.Products;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.CategoryService;
import com.zw.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private CategoryService categoryService;

    // 获取所有分类
    @Authorize
    @GetMapping("/category")
    public JsonResult<List<Category>> getAllCategory(){
        return new JsonResult(200, categoryService.list());
    }

    // 查询所有商品
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Products>> list(){
        List<Products> list = productsService.saveBatchProducts();
        return new JsonResult<>(200,list);
    }

    // 添加商品
    @Authorize
    @PostMapping("/add")
    public JsonResult<Void> add(@RequestBody Products products){
        products.setIsActivate(1);
        productsService.save(products);
        return new JsonResult<>(200);
    }

    // 按id删除商品
    @Authorize
    @DeleteMapping("/delete/{id}")
    public JsonResult<Void> delete(@PathVariable("id") Integer id){
        productsService.removeById(id);
        return new JsonResult<>(200);
    }

    // 根据id查询商品
    @Authorize
    @GetMapping("/get/{id}")
    public JsonResult<Products> get(@PathVariable("id") Integer id){
        Products products = productsService.getById(id);
        return new JsonResult<>(200, products);
    }

    // 根据id修改商品
    @Authorize
    @PutMapping("/update")
    public JsonResult<Void> update(@RequestBody Products products){
        productsService.updateById(products);
        return new JsonResult<>(200);
    }
}
