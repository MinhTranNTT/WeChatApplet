package com.zw.api.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.ObjectMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import com.zw.api.config.WeChatPayConfig;

import com.zw.api.utils.RedisExpiredListener;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Coupon;
import com.zw.common.domain.Order;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.CouponMapper;
import com.zw.mapper.OrderMapper;
import com.zw.service.BatchService;
import com.zw.service.OrderService;
import com.zw.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/wxPay")
public class WxPayController {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    WeChatPayConfig weChatPayConfig;
    @Autowired
    OrderService orderService;

    @Autowired
    BatchService batchService;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RedisExpiredListener redisExpiredListener;
    @Autowired
    RedisService redisService;
    @Autowired
    CouponMapper couponMapper;


    // 小程序端调用的生成预支付订单并返回正式支付所需信息的接口
    @Authorize
    @PostMapping("/pay")
    public JsonResult<Map<String,String>> Payment(@RequestBody Order order, HttpServletRequest request) throws WxPayException {
        String notifyUrl = weChatPayConfig.getNotifyUrl();
        // 1. 生成预支付订单
        // 1.1 生成订单信息
        // 添加openid
        String openid = (String) request.getAttribute("openid");
        order.setOpenid(openid);
        // 生成订单号sn
        Snowflake snowflake = new Snowflake(1, 1);
        long sn = snowflake.nextId();
        order.setSn(Long.toString(sn));
        // 设置订单状态为待支付
        order.setStatus(0);
        // 设置订单生成时间
        DateTime now = DateTime.now();
        order.setCreateTime(now);
        // 设置订单为不删除
        order.setIsDelete(0);

        // 更新coupon信息
        Coupon coupon = order.getCoupon();
        if(coupon!=null){
            coupon.setStatus(1);
            coupon.setUseTime(now);
            coupon.setUseOrderSn(order.getSn());
            couponMapper.updateById(coupon);
        }
        // 将订单信息添加到数据库
        orderService.save(order);



        /**
         * 更新商品的批次信息，减少对应的批次数量
         */
        // 构造批次信息
        JSONObject json = order.getCamperInfo();
        Batch batch = json.toJavaObject(Batch.class);
        // 如果批次剩余为0，则该批次已售罄，将剩余人数设置为-1，代表已售罄
        if(batch.getRemains()==0){
            batch.setRemains(-1);
        }
        // 更新批次信息
        batchService.updateById(batch);
        // 将预支付订单信息存入redis，并设置过期时间为30分钟
        redisService.setex(redisExpiredListener.UNPAID_PREFIX+ sn,sn,30*60);
        /**
         * 1.2 请求微信接口，生成prepayId
         */
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody(order.getProductName());
        orderRequest.setOutTradeNo(order.getSn().toString());
        orderRequest.setTotalFee(order.getPayPrice());
        orderRequest.setSpbillCreateIp(InetAddress.getLoopbackAddress().getHostAddress());
        orderRequest.setNotifyUrl(notifyUrl);
        orderRequest.setTradeType("JSAPI");
        orderRequest.setOpenid(order.getOpenid());

        // Send the request to the WeChat Pay API to create the payment order
        WxPayUnifiedOrderResult orderResult = wxPayService.unifiedOrder(orderRequest);
        String prepayId = orderResult.getPrepayId();

        // Calculate the paySign
        Map<String,String> payInfo = new HashMap<>();
//        JSONObject payInfo = new JSONObject();
        payInfo.put("appId",orderResult.getAppid());
        payInfo.put("timeStamp",String.valueOf(System.currentTimeMillis()));
        payInfo.put("nonceStr",orderResult.getNonceStr().toString());
        payInfo.put("package","prepay_id=" + prepayId.toString());
        payInfo.put("signType","RSA");
        String paySign = SignUtils.createSign(payInfo,"RSA", wxPayService.getConfig().getMchKey(),null);
        payInfo.put("paySign",paySign);
        payInfo.put("sn",order.getSn().toString());
        return new JsonResult<>(200,payInfo);
    }

    // 微信返回的流
//    @RequestMapping(value = "/notify",method = {RequestMethod.POST,RequestMethod.GET})
    @PostMapping("/notify")
    public String weChatNotify(@RequestBody String xmlData){
        try{
            final WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            Order order = new Order();
            order.setSn(notifyResult.getOutTradeNo());
            order.setPayTime(new DateTime());
            order.setPayOrderId(notifyResult.getTransactionId());
            order.setStatus(1);
            orderMapper.updateBySn(order);
            // 支付成功，删除redis中的订单信息
            redisService.del(redisExpiredListener.UNPAID_PREFIX+order.getSn());
            return WxPayNotifyResponse.success("OK");
        } catch (WxPayException e) {
            e.printStackTrace();
            System.out.println("fail");
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }

}
