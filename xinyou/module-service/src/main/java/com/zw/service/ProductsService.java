package com.zw.service;

import com.zw.common.domain.Products;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author aking
* @description 针对表【mall_products】的数据库操作Service
* @createDate 2022-12-10 18:31:52
*/
public interface ProductsService extends IService<Products> {

    //将商品的batch数据存入products表中
    List<Products> saveBatchProducts();

    //同上，根据商品类型id取出商品列表
    List<Products> saveBatchProductsByCategoryId(Integer categoryId);

    //根据商品id获取商品信息
    Products selectById(Integer id);
}
