package com.zw.api.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.zw.api.utils.RedisExpiredListener;
import com.zw.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WeChatPayConfig {

    @Value("${wx.pay.appid}")
    private String appid;
    @Value("${wx.pay.mchId}")
    private String mchId;
    @Value("${wx.pay.mchKey}")
    private String mchKey;
    @Value("${wx.pay.keyPath}")
    private String keyPath;
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;
    @Value("${wx.pay.refundUrl}")
    private String returnUrl;
    @Value("${wx.pay.tradeType}")
    private String tradeType;

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig payConfig(){
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(appid);
        payConfig.setMchId(mchId);
        payConfig.setMchKey(mchKey);
        payConfig.setKeyPath(keyPath);
//        payConfig.setNotifyUrl(notifyUrl);
        payConfig.setTradeType(tradeType);
        return payConfig;
    }

    @Bean
    public WxPayService wxPayService(WxPayConfig payConfig){
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    public String getAppid() {
        return appid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getTradeType() {
        return tradeType;
    }
}
