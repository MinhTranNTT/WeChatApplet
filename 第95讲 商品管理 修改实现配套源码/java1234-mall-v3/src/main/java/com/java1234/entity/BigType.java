package com.java1234.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 商品大类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2021-11-22 22:03
 */
@TableName("t_bigType")
@Data
public class BigType {

    private Integer id; // 编号

    private String name; // 名称

    private String remark; // 备注

    private String image="default.jpg"; // 封面图片

    @TableField(select=false)
    private List<SmallType> smallTypeList; // 商品小类集合


}
