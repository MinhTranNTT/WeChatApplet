package com.zw;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.crypto.SignUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.zw.api.config.WeChatPayConfig;
import com.zw.common.domain.Banner;
import com.zw.common.domain.Batch;
import com.zw.mapper.BannerMapper;
import com.zw.service.BatchService;
import com.zw.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.*;

@SpringBootTest(classes = com.zw.api.ApiApplication.class)
public class ApiTest {
    @Autowired
    BannerMapper bannerMapper;
//    @Test
//    void sqltest(){
//        List<Banner> bannerListByProductId = bannerMapper.getBannerListByProductId(1);
//        System.out.println(bannerListByProductId);
//    }
    @Test
    void urlTest() {
        String url = "http://localhost/file/oMrPf5P/b2b350ae-e14b-4047-97db-c14fa9da738f.jpg";
        String path = url.split("file/")[1];
        System.out.println(path);
    }

    // 测试微信支付
    @Test
    void wxPayTest() throws WxPayException {
        DateTime now = DateTime.now();
        String timeStamp = now.toString();
        System.out.println(timeStamp);
        System.out.println(System.currentTimeMillis());
        System.out.println(String.valueOf(System.currentTimeMillis()));
        // 2. 生成签名
        // 3. 返回给前端

    }

    // 生成加密的随机数
    @Test
    void randomTest() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        System.out.println(Base64.getEncoder().encodeToString(bytes));
//        Base64.getEncoder().encodeToString(bytes);
    }

    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WeChatPayConfig weChatPayConfig;

    // 测试微信支付
    @Test
    void wxPayTest2() throws WxPayException {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody("测试商品");
        orderRequest.setOutTradeNo("46789038746532784637281726346356");
        orderRequest.setTotalFee(1);
        orderRequest.setSpbillCreateIp(InetAddress.getLoopbackAddress().getHostAddress());
        orderRequest.setTradeType("JSAPI");
        orderRequest.setNotifyUrl(weChatPayConfig.getNotifyUrl());
        orderRequest.setOpenid("oMrPf5Pa7G4dRrGj_MUjpuxTPLQU");

        // Send the request to the WeChat Pay API to create the payment order
        WxPayUnifiedOrderResult orderResult = wxPayService.unifiedOrder(orderRequest);
        String prepayId = orderResult.getPrepayId();

        // Calculate the paySign
        Map<String, String> payInfo = new HashMap<>();
        payInfo.put("appId", orderResult.getAppid());
        payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis()));
        payInfo.put("nonceStr", orderResult.getNonceStr());
        payInfo.put("package", "prepay_id=" + prepayId);
        payInfo.put("signType", "RSA");
//        String paySign = SignUtil.signParamsSha256(payInfo, wxPayService.getConfig().getMchKey());
        String paySign = SignUtils.createSign(payInfo,"RSA", wxPayService.getConfig().getMchKey(),null);
        System.out.println(paySign);
        payInfo.put("paySign", paySign);
        System.out.println(payInfo);
    }

    // 测试雪花算法
    @Test
    void snowFlakeTest() {
        Snowflake snowflake = new Snowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowflake.nextId());
        }
    }

    // 测试时间
    @Test
    void timeTest() {
        DateTime now = DateTime.now();
        System.out.println(now);
        System.out.println(now.toString());
        System.out.println(now.toString("yyyy-MM-dd HH:mm:ss"));
    }

    // 测试实体类型转换
    @Test
    void entityTest() {
        JSONObject json=new JSONObject();
        json.append("beginTime", "1670601600000");
        json.append("endTime", "1670947200000");
        json.append("id", 1);
        json.append("productId", 1);
        json.append("remains", 1);
        json.append("stock", 1);
//        Batch batch = json.toBean(Batch.class);
        json.toBean(Batch.class);
//        System.out.println(batch);
    }

    @Autowired
    RedisService redisService;
    // 测试redisson
    @Test
    void redissonTest() {
        redisService.setex("unpaidOrder_test", "test", 60);
    }

    @Autowired
    BatchService batchService;
    // 测试订单完成
    @Test
    void orderTest() {
        Batch batch = batchService.getById(1);
        System.out.println(batch.getEndTime());
        Date date = new Date();
        if(batch.getEndTime().before(date)) {
            System.out.println("过期");
        } else {
            System.out.println("未过期");
        }
    }


    @Test
    void test() {
        System.out.println(weChatPayConfig.getNotifyUrl());
    }

    //测试时间戳
    @Test
    void timeTest2() {
        System.out.println(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000));
    }
}
