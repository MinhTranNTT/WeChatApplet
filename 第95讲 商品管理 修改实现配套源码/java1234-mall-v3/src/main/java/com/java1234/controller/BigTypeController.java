package com.java1234.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java1234.entity.BigType;
import com.java1234.entity.Product;
import com.java1234.entity.R;
import com.java1234.entity.SmallType;
import com.java1234.service.IBigTypeService;
import com.java1234.service.IProductService;
import com.java1234.service.ISmallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品大类Controller
 * @author java1234_小锋
 * @site www.java1234.com
 * @company 南通小锋网络科技有限公司
 * @create 2022-02-23 22:03
 */
@RestController
@RequestMapping("/bigType")
public class BigTypeController {

    @Autowired
    private IBigTypeService bigTypeService;

    @Autowired
    private ISmallTypeService smallTypeService;

    @Autowired
    private IProductService productService;

    /**
     * 查询所有商品大类
     * @return
     */
    @GetMapping("/findAll")
    public R findAll(){
        List<BigType> bigTypeList = bigTypeService.list();
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }

    /**
     * 获取所有菜单信息
     * @return
     */
    @GetMapping("/findCategories")
    public R findCategories(){
        List<BigType> bigTypeList = bigTypeService.list();
        for(BigType bigType:bigTypeList){
            List<SmallType> smallTypeList = smallTypeService.list(new QueryWrapper<SmallType>().eq("bigTypeId", bigType.getId()));
            bigType.setSmallTypeList(smallTypeList);
            for(SmallType smallType:smallTypeList){
                List<Product> productList = productService.list(new QueryWrapper<Product>().eq("typeId", smallType.getId()));
                smallType.setProductList(productList);
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message",bigTypeList);
        return R.ok(map);
    }

}
