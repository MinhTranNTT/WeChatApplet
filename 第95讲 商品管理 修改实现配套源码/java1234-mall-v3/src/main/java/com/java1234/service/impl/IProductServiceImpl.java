package com.java1234.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.java1234.entity.Product;
import com.java1234.mapper.ProductMapper;
import com.java1234.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品Service实现类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-23 22:02
 */
@Service("productService")
public class IProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> list(Map<String, Object> map) {
        return productMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return productMapper.getTotal(map);
    }

    @Override
    public void add(Product product) {
        productMapper.add(product);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }
}
