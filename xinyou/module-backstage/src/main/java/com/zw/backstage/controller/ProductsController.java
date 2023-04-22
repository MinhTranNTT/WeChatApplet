package com.zw.backstage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Category;
import com.zw.common.domain.Products;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.UploadFile;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.BatchMapper;
import com.zw.service.CategoryService;
import com.zw.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.cache.annotation.CacheRemoveAll;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    UploadFile uploadFile;
    @Autowired
    BatchMapper batchMapper;

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
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> add(@RequestBody Products products){
//        products.setIsActivate(1);
        productsService.save(products);
        return new JsonResult<>(200);
    }

    // 按id删除商品
    @Authorize
    @DeleteMapping("/delete/{id}")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> delete(@PathVariable("id") Integer id){
        // 首先从数据库中获取商品信息
        Products products = productsService.getById(id);
        // 分别获取bannerList和detailList以及productPoster
        JSONArray bannerList = products.getBannerList();
        JSONArray detailList = products.getDetailList();
        String productPoster = products.getProductPoster();
        // 删除bannerList中的图片
        for (int i = 0; i < bannerList.size(); i++) {
            // 将每个元素读取为JSONObject
            JSONObject jsonObject = bannerList.getJSONObject(i);
            // 获取每个元素的url
            String url = jsonObject.getString("url");
            // 从file后面分割字符串
            String split = url.split("file/")[1];
            // 删除图片
            uploadFile.deleteFile(split);
        }
        // 删除detailList中的图片
        for (int i = 0; i < detailList.size(); i++) {
            // 将每个元素读取为JSONObject
            JSONObject jsonObject = detailList.getJSONObject(i);
            // 获取每个元素的url
            String url = jsonObject.getString("url");
            // 从file后面分割字符串
            String split = url.split("file/")[1];
            // 删除图片
            uploadFile.deleteFile(split);
        }
        // 删除productPoster
//        String split = productPoster.split("file/")[1];
//        uploadFile.deleteFile(split);
        // 删除相关的batch
        List<Batch> batchList = batchMapper.selectAllByProductId(id);
        for (Batch batch : batchList) {
            batchMapper.deleteById(batch.getId());
        }

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
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> update(@RequestBody Products products){
        productsService.updateById(products);
        return new JsonResult<>(200);
    }
}
