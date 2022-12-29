package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Category;
import com.zw.common.domain.Products;
import com.zw.mapper.BatchMapper;
import com.zw.mapper.CategoryMapper;
import com.zw.mapper.ProductsMapper;
import com.zw.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author aking
 * @description 针对表【mall_products】的数据库操作Service实现
 * @createDate 2022-12-10 18:31:52
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products>
        implements ProductsService {

    @Autowired
    ProductsMapper productsMapper;
    @Autowired
    BatchMapper batchMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Products> saveBatchProducts() {
        //读取商品列表
        List<Products> productsList = productsMapper.selectAllAndOrderByProductsOrder();
        //遍历商品列表
        for (Products product : productsList) {
            //直接从商品表中获取商品id
            Integer productId = product.getId();
            //从商品表中获取类型id
            Integer categoryId = product.getCategoryId();
            //根据类型id获取类型名称
            Category category = categoryMapper.selectById(categoryId);
            String categoryName = category.getName();
            //根据商品id获取批次列表
            List<Batch> batchList = batchMapper.selectAllByProductId(productId);
            //将批次列表设置到商品对象中
            product.setBatchList(batchList);
            product.setCategoryName(categoryName);
        }
        return productsList;
    }

    @Override
    public List<Products> saveBatchProductsByCategoryId(Integer category_Id) {
        //读取商品列表
        List<Products> productsList = productsMapper.selectAllByCategoryId(category_Id);
        //遍历商品列表
        for (Products product : productsList) {
            //直接从商品表中获取商品id
            Integer productId = product.getId();
            //从商品表中获取类型id
            Integer categoryId = product.getCategoryId();
            //根据类型id获取类型名称
            Category category = categoryMapper.selectById(categoryId);
            String categoryName = category.getName();
            //根据商品id获取批次列表
            List<Batch> batchList = batchMapper.selectAllByProductId(productId);
            //将批次列表设置到商品对象中
            product.setBatchList(batchList);
            product.setCategoryName(categoryName);
        }
        return productsList;
    }

    @Override
    public Products selectById(Integer id) {
        Products product = productsMapper.selectById(id);
        //直接从商品表中获取商品id
        Integer productId = product.getId();
        //从商品表中获取类型id
        Integer categoryId = product.getCategoryId();
        //根据类型id获取类型名称
        Category category = categoryMapper.selectById(categoryId);
        String categoryName = category.getName();
        //根据商品id获取批次列表
        List<Batch> batchList = batchMapper.selectAllByProductId(productId);
        //将批次列表设置到商品对象中
        product.setBatchList(batchList);
        product.setCategoryName(categoryName);
        return product;
    }

}




