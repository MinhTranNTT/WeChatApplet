package com.java1234.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java1234.entity.SmallType;

import java.util.List;
import java.util.Map;

/**
 * 商品小类Service接口
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-23 22:01
 */
public interface ISmallTypeService extends IService<SmallType> {
    List<SmallType> list(Map<String,Object> map);

    Long getTotal(Map<String,Object> map);
}
