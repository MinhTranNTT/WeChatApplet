package com.zw.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Banner;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_banner(Banner管理)】的数据库操作Mapper
* @createDate 2022-12-10 18:31:52
* @Entity generator.domain.Banner
*/
@Mapper
public interface BannerMapper extends BaseMapper<Banner> {

    // 根据sort字段顺序获取banner列表

    List<Banner> getBannerListBySort();

    // 根据productId获取banner列表

    List<Banner> getBannerListByProductId(Integer productId);
}




