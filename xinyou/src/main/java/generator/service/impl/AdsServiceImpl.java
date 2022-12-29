package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Ads;
import generator.service.AdsService;
import generator.mapper.AdsMapper;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_ads】的数据库操作Service实现
* @createDate 2022-12-29 10:12:20
*/
@Service
public class AdsServiceImpl extends ServiceImpl<AdsMapper, Ads>
    implements AdsService{

}




