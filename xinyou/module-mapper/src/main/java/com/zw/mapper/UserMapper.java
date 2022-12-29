package com.zw.mapper;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_user】的数据库操作Mapper
* @createDate 2022-12-20 09:34:47
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //根据openid更新信息
    int updateUserByOpenid(User user);
    //根据openid查询用户信息
    User getOneByOpenid(@Param("openid") String openid);

    // 根据openid获取登陆时间
    Date getRegisterTimeByOpenid(@Param("openid") String openid);

    // 检查user是否存在
    int checkUserExist(@Param("openid") String openid);
}




