package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Admin;
import generator.service.AdminService;
import generator.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author anking
* @description 针对表【backstage_admin】的数据库操作Service实现
* @createDate 2023-01-01 13:12:41
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




