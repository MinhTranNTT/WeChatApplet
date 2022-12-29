package com.zw.api.controller;

import com.zw.common.domain.Products;
import com.zw.common.utils.JsonResult;
import com.zw.mapper.ProductsMapper;
import com.zw.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductsService productsService;
    @Autowired
    ProductsMapper productsMapper;
    //获取所有商品
    @GetMapping("/list")
    public JsonResult<List<Products>> getAll(){
        return new JsonResult(200,productsService.list());
    }

    // 添加商品
    @PostMapping("/save")
    public JsonResult<Boolean> saveProduct(@RequestBody Products products){
        return new JsonResult(200, productsService.save(products));
    }

    // 更新商品
    @PutMapping("/update")
    public JsonResult<Boolean> updateProduct(@RequestBody Products products){
        return new JsonResult(200, productsService.updateById(products));
    }

    // 删除商品
    @DeleteMapping("/delete/{id}")
    public JsonResult<Boolean> deleteProduct(@PathVariable Integer id){
        return new JsonResult(200, productsService.removeById(id));
    }
    //根据商品id获取单个商品
    @GetMapping("/getOne/{id}")
    JsonResult<Products> getOneProduct(@PathVariable Integer id){
        return new JsonResult(200, productsService.getById(id));
    }

    //返回包含商品批次的商品列表
    @Cacheable(value = "products", key = "'products'")
    @GetMapping("/getProductsList")
    public JsonResult<List<Products>> getProductsList(){
        return new JsonResult(200, productsService.saveBatchProducts());
    }

    //根据商品类型返回商品列表
    @Cacheable(value = "products", key = "'category'+{#category_id}")
    @GetMapping("/getProductsListByType/{category_id}")
    public JsonResult<List<Products>> getProductsListByType(@PathVariable Integer category_id){
        return new JsonResult(200, productsService.saveBatchProductsByCategoryId(category_id));
    }

    //根据商品id获取商品信息
    @Cacheable(value = "products", key = "'product'+{#id}")
    @GetMapping("/getProductById/{id}")
    public JsonResult<Products> getProductById(@PathVariable Integer id){
        return new JsonResult(200, productsService.selectById(id));
    }

    //营期部分
    @GetMapping("/getProductsListByBatch/{id}")
    public JsonResult<List<Products>> getProductsListByBatch(@PathVariable Integer id){
        return new JsonResult(200, productsService.selectById(id));
    }
}
