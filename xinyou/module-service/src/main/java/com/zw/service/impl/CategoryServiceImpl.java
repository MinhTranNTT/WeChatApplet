package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zw.common.domain.Category;
import com.zw.mapper.CategoryMapper;
import com.zw.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_category】的数据库操作Service实现
* @createDate 2022-12-10 18:31:52
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

}




