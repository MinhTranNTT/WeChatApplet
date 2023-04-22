package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Banner;
import generator.service.BannerService;
import generator.mapper.BannerMapper;
import org.springframework.stereotype.Service;

/**
* @author aking
* @description 针对表【mall_banner(Banner管理)】的数据库操作Service实现
* @createDate 2023-04-17 14:22:28
*/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
    implements BannerService{

}




