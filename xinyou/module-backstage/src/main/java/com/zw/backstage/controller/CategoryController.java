package com.zw.backstage.controller;

import com.zw.common.domain.Category;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.cache.annotation.CacheRemoveAll;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    // 获取分类列表
    @Authorize
    @GetMapping("/list")
    public JsonResult<List<Category>> getAllCategory() {
        return new JsonResult<List<Category>>(200, categoryService.list());
    }

    // 根据id删除分类
    @Authorize
    @DeleteMapping("/delete/{id}")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> delete(@PathVariable("id") Integer id) {
        categoryService.removeById(id);
        return new JsonResult<>(200);
    }

    // 根据id修改分类
    @Authorize
    @PutMapping("/update")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return new JsonResult<>(200);
    }

    // 添加分类
    @Authorize
    @PostMapping("/add")
    @CacheRemoveAll(cacheName = "products")
    public JsonResult<Void> add(@RequestBody Category category) {
        categoryService.save(category);
        return new JsonResult<>(200);
    }
}
