package com.zw.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Batch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author aking
* @description 针对表【mall_batch】的数据库操作Mapper
* @createDate 2022-12-10 18:31:52
* @Entity generator.domain.Batch
*/
@Mapper
public interface BatchMapper extends BaseMapper<Batch> {

    //根据商品id获取批次列表
    List<Batch> selectAllByProductId(@Param("productId") Integer productId);
}




