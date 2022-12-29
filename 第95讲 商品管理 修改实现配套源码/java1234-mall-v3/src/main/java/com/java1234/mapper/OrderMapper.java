package com.java1234.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java1234.entity.Order;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-23 22:00
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> list(Map<String,Object> map);

    Long getTotal(Map<String,Object> map);
}
