package com.zw.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Ads;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_ads】的数据库操作Mapper
* @createDate 2022-12-29 10:12:20
* @Entity generator.domain.Ads
*/
@Mapper
public interface AdsMapper extends BaseMapper<Ads> {
    // 根据is_show字段查询单个广告
    Ads selectOneByIsShowLimit1();

}




