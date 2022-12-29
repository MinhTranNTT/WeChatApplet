package com.zw.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @author aking
* @description 针对表【mall_order】的数据库操作Mapper
* @createDate 2022-12-24 10:31:44
* @Entity generator.domain.Order
*/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    // 根据sn更新订单信息
    Boolean updateBySn(Order order);

    //根据订单状态和用户openid获取订单列表
    List<Order> getAllByStatusAndOpenid(@Param("status") Integer status, @Param("openid") String openid);

    //根据openid获取订单列表
    List<Order> getAllByOpenid(@Param("openid") String openid);

    List<Order> getAllByOpenidOrderByCreateTimeDesc(@Param("openid") String openid);
    // 根据sn获取订单信息
    Order getOneBySn(@Param("sn") String sn);

    // 根据订单状态获取订单列表
    List<Order> getByStatus(@Param("status") Integer status);
}




