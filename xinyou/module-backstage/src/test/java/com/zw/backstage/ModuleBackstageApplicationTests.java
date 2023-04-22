package com.zw.backstage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zw.common.domain.Products;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModuleBackstageApplicationTests {

    @Autowired
    private ProductsService productsService;
    @Test
    void contextLoads() {
    }

    @Test
    void ssss(){
        Products products = productsService.getById(17);
        System.out.println(products);
        JSONArray bannerList = products.getDetailList();
        System.out.println(bannerList);
        for(int i=0;i<bannerList.size();i++){
            // 将每个元素读取为JSONObject
            JSONObject jsonObject = bannerList.getJSONObject(i);
            // 获取每个元素的url
            String url = jsonObject.getString("url");
            System.out.println(url);
            // 从file后面分割字符串
            String split = url.split("file/")[1];
            System.out.println(split);
//            System.out.println(bannerList.get(i));
        }
        String productPoster = products.getProductPoster();
        System.out.println(productPoster);
        String splite1 = productPoster.split("file/")[1];
        System.out.println(splite1);
    }
}
