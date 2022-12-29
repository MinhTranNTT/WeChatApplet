package com.zw.api.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.zw.api.config.WeChatPayConfig;
import com.zw.common.domain.Batch;
import com.zw.common.domain.Coupon;
import com.zw.common.domain.Order;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.CouponMapper;
import com.zw.mapper.OrderMapper;
import com.zw.service.BatchService;
import com.zw.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user/wxRefund")
public class WxRefundController {
    private static final String REFUND_SUCCESS = "SUCCESS";
    @Autowired
    WeChatPayConfig weChatPayConfig;
    @Autowired
    private WxPayService wxPayService;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BatchService batchService;
    @Autowired
    CouponMapper couponMapper;
    // 退款
    @Authorize
    @PostMapping("/refund")
    public String refund(@RequestBody Order order){
        String refundUrl = weChatPayConfig.getReturnUrl();

        if (order.getRefundStatus()==2){
            return "已退款";
        }

        WxPayRefundRequest refundInfo = WxPayRefundRequest.newBuilder()
                .outTradeNo(order.getSn())
                .outRefundNo(order.getSn())
                .totalFee(order.getPayPrice())
                .refundFee(order.getPayPrice())
                .refundDesc("退款")
                .notifyUrl(refundUrl)
                .build();
        WxPayRefundResult wxPayRefundResult;
        try{
            wxPayRefundResult = wxPayService.refund(refundInfo);
            if (REFUND_SUCCESS.equals(wxPayRefundResult.getReturnCode())) {
                return "退款成功";
            }
        } catch (WxPayException e) {
            throw new RuntimeException(e);
        }
        return "退款失败";
    }

    //退款回调
    @PostMapping("/refundNotify")
    public String refundNotify(@RequestBody String xmlData) {
        WxPayRefundNotifyResult wxPayRefundNotifyResult;
        try{
            wxPayRefundNotifyResult = wxPayService.parseRefundNotifyResult(xmlData);
            if (REFUND_SUCCESS.equals(wxPayRefundNotifyResult.getReturnCode())) {
                WxPayRefundNotifyResult.ReqInfo reqInfo = wxPayRefundNotifyResult.getReqInfo();
                System.out.println(wxPayRefundNotifyResult);
                System.out.println(reqInfo);
                // 根据sn获取订单信息
                Order order = orderMapper.getOneBySn(reqInfo.getOutTradeNo());
                // 修改订单状态
                order.setRefundStatus(2);
                order.setRefundPrice(reqInfo.getRefundFee());
                order.setStatus(3);
                order.setCancelType(1);
                order.setCalcelTime(new Date());
                Batch batch = order.getCamperInfo().toJavaObject(Batch.class);
                if(batch.getRemains()==-1){
                    batch.setRemains(0);
                }
                batch.setRemains(batch.getRemains()+order.getProductCount());

                // 更新coupon信息,根据couponId获取coupon信息
                Coupon coupon = couponMapper.selectById(order.getCouponId());
                if(coupon!=null){
                    coupon.setUseTime(null);
                    coupon.setStatus(0);
                    coupon.setUseOrderSn("0");
                    couponMapper.updateById(coupon);
                }

                batchService.updateById(batch);
                orderService.updateById(order);

                return WxPayNotifyResponse.success("退款成功");
            }
        } catch (WxPayException e) {
            throw new RuntimeException(e);
        }
        if(REFUND_SUCCESS.equals(wxPayRefundNotifyResult.getReturnCode())){
            WxPayRefundNotifyResult.ReqInfo reqInfo = wxPayRefundNotifyResult.getReqInfo();
            if(REFUND_SUCCESS.equals(reqInfo.getRefundStatus())) {
                String outTradeNo = reqInfo.getOutTradeNo();
                System.out.println("退款成功"+outTradeNo);
                return WxPayNotifyResponse.success("退款成功");
                //退款成功
            }
        }
        return WxPayNotifyResponse.fail("回调失败");
    }
}
