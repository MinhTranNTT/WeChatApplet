package com.zw.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Camper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_camper】的数据库操作Mapper
* @createDate 2022-12-20 09:34:47
* @Entity generator.domain.Camper
*/
@Mapper
public interface CamperMapper extends BaseMapper<Camper> {
    // 根据openid获取所有营员列表
    List<Camper> getAllByOpenid(@Param("openid") String openid);

    // 根据openid修改营员信息
    Boolean updateCamperByOpenid(Camper camper);

    // 根据openid删除营员
    int deleteByOpenid(@Param("openid") String openid);

    // 根据openid获取营员照片
    String getPhotoUrlByOpenid(@Param("openid") String openid);


}




