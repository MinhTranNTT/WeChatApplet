package com.zw.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_category】的数据库操作Mapper
* @createDate 2022-12-10 18:31:52
* @Entity generator.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




