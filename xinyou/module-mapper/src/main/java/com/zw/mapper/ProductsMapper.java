package com.zw.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Products;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_products】的数据库操作Mapper
* @createDate 2022-12-10 18:31:52
* @Entity generator.domain.Products
*/
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {
    //根据商品类型获取所有商品
    List<Products> selectAllByCategoryId(@Param("categoryId") Integer categoryId);

    // 根据products_order升序获取所有商品

    List<Products> selectAllAndOrderByProductsOrder();
}




