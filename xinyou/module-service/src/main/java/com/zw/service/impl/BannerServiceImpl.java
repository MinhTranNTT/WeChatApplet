package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.common.domain.Banner;
import com.zw.mapper.BannerMapper;
import com.zw.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_banner(Banner管理)】的数据库操作Service实现
* @createDate 2022-12-10 18:31:52
*/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
    implements BannerService{

    @Autowired
    BannerMapper bannerMapper;
}




