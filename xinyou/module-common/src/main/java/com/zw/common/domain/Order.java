package com.zw.common.domain;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * 
 * @TableName mall_order
 */
@TableName(value ="mall_order",autoResultMap = true)
@Data
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 下单用户openid
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 订单流水号
     */
    @TableField(value = "sn")
    private String sn;

    /**
     * 0：普通订单，1：拼团订单
     */
    @TableField(value = "order_type")
    private Integer orderType;

    /**
     * 订单类型：0:待付款，1：已付款，2：已完成，3：已取消
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * [0:超时未支付 1:退款关闭 2:买家取消 ]
     */
    @TableField(value = "cancel_type")
    private Integer cancelType;

    /**
     * 订单创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 订单支付时间
     */
    @TableField(value = "pay_time")
    private Date payTime;

    /**
     * 订单完成时间
     */
    @TableField(value = "finish_time")
    private Date finishTime;

    /**
     * 订单取消时间
     */
    @TableField(value = "calcel_time")
    private Date calcelTime;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品图片
     */
    @TableField(value = "product_image")
    private String productImage;

    /**
     * 启程集合地点
     */
    @TableField(value = "register_location")
    private String registerLocation;

    /**
     * 返程集合地点
     */
    @TableField(value = "return_location")
    private String returnLocation;
    /**
     * 选择的营期信息
     */
    @TableField(value = "camper_info",typeHandler = JacksonTypeHandler.class)
    private JSONObject camperInfo;

    /**
     * 购买得商品得数量
     */
    @TableField(value = "product_count")
    private Integer productCount;

    /**
     * 此订单得营员信息
     */
    @TableField(value = "camper_list",typeHandler = JacksonTypeHandler.class)
    private JSONArray camperList;

    /**
     * 商品原价：单位：分
     */
    @TableField(value = "original_price")
    private Integer originalPrice;

    /**
     * 优惠金额，单位：分
     */
    @TableField(value = "promotion_price")
    private Integer promotionPrice;

    /**
     * 优惠券编号
     */
    @TableField(value = "coupon_id")
    private String couponId;

    /**
     * 应付金额，单位：分
     */
    @TableField(value = "pay_price")
    private Integer payPrice;

    /**
     * 支付订单编号
     */
    @TableField(value = "pay_order_id")
    private String payOrderId;

    /**
     * 退款状态:[0:未申请退款 1:申请退款 2:等待退款 3:退款成功]
     */
    @TableField(value = "refund_status")
    private Integer refundStatus;

    /**
     * 退款金额：单位：分
     */
    @TableField(value = "refund_price")
    private Integer refundPrice;

    /**
     * 是否删除：0：不删除，1：删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    // 优惠券信息
    @TableField(exist = false)
    private Coupon coupon;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getSn() == null ? other.getSn() == null : this.getSn().equals(other.getSn()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCancelType() == null ? other.getCancelType() == null : this.getCancelType().equals(other.getCancelType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getFinishTime() == null ? other.getFinishTime() == null : this.getFinishTime().equals(other.getFinishTime()))
            && (this.getCalcelTime() == null ? other.getCalcelTime() == null : this.getCalcelTime().equals(other.getCalcelTime()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getProductImage() == null ? other.getProductImage() == null : this.getProductImage().equals(other.getProductImage()))
            && (this.getRegisterLocation() == null ? other.getRegisterLocation() == null : this.getRegisterLocation().equals(other.getRegisterLocation()))
            && (this.getReturnLocation() == null ? other.getReturnLocation() == null : this.getReturnLocation().equals(other.getReturnLocation()))
            && (this.getCamperInfo() == null ? other.getCamperInfo() == null : this.getCamperInfo().equals(other.getCamperInfo()))
            && (this.getProductCount() == null ? other.getProductCount() == null : this.getProductCount().equals(other.getProductCount()))
            && (this.getCamperList() == null ? other.getCamperList() == null : this.getCamperList().equals(other.getCamperList()))
            && (this.getOriginalPrice() == null ? other.getOriginalPrice() == null : this.getOriginalPrice().equals(other.getOriginalPrice()))
            && (this.getPromotionPrice() == null ? other.getPromotionPrice() == null : this.getPromotionPrice().equals(other.getPromotionPrice()))
            && (this.getCouponId() == null ? other.getCouponId() == null : this.getCouponId().equals(other.getCouponId()))
            && (this.getPayPrice() == null ? other.getPayPrice() == null : this.getPayPrice().equals(other.getPayPrice()))
            && (this.getPayOrderId() == null ? other.getPayOrderId() == null : this.getPayOrderId().equals(other.getPayOrderId()))
            && (this.getRefundStatus() == null ? other.getRefundStatus() == null : this.getRefundStatus().equals(other.getRefundStatus()))
            && (this.getRefundPrice() == null ? other.getRefundPrice() == null : this.getRefundPrice().equals(other.getRefundPrice()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
                && (this.getCoupon() == null ? other.getCoupon() == null : this.getCoupon().equals(other.getCoupon()));}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getSn() == null) ? 0 : getSn().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCancelType() == null) ? 0 : getCancelType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getFinishTime() == null) ? 0 : getFinishTime().hashCode());
        result = prime * result + ((getCalcelTime() == null) ? 0 : getCalcelTime().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getProductImage() == null) ? 0 : getProductImage().hashCode());
        result = prime * result + ((getRegisterLocation() == null) ? 0 : getRegisterLocation().hashCode());
        result = prime * result + ((getReturnLocation() == null) ? 0 : getReturnLocation().hashCode());
        result = prime * result + ((getCamperInfo() == null) ? 0 : getCamperInfo().hashCode());
        result = prime * result + ((getProductCount() == null) ? 0 : getProductCount().hashCode());
        result = prime * result + ((getCamperList() == null) ? 0 : getCamperList().hashCode());
        result = prime * result + ((getOriginalPrice() == null) ? 0 : getOriginalPrice().hashCode());
        result = prime * result + ((getPromotionPrice() == null) ? 0 : getPromotionPrice().hashCode());
        result = prime * result + ((getCouponId() == null) ? 0 : getCouponId().hashCode());
        result = prime * result + ((getPayPrice() == null) ? 0 : getPayPrice().hashCode());
        result = prime * result + ((getPayOrderId() == null) ? 0 : getPayOrderId().hashCode());
        result = prime * result + ((getRefundStatus() == null) ? 0 : getRefundStatus().hashCode());
        result = prime * result + ((getRefundPrice() == null) ? 0 : getRefundPrice().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCoupon() == null) ? 0 : getCoupon().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", openid=").append(openid);
        sb.append(", sn=").append(sn);
        sb.append(", orderType=").append(orderType);
        sb.append(", status=").append(status);
        sb.append(", cancelType=").append(cancelType);
        sb.append(", createTime=").append(createTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", calcelTime=").append(calcelTime);
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productImage=").append(productImage);
        sb.append(", camperInfo=").append(camperInfo);
        sb.append(", productCount=").append(productCount);
        sb.append(", registerLocation=").append(registerLocation);
        sb.append(", returnLocation=").append(returnLocation);
        sb.append(", camperList=").append(camperList);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", promotionPrice=").append(promotionPrice);
        sb.append(", couponId=").append(couponId);
        sb.append(", payPrice=").append(payPrice);
        sb.append(", payOrderId=").append(payOrderId);
        sb.append(", refundStatus=").append(refundStatus);
        sb.append(", refundPrice=").append(refundPrice);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", coupon=").append(coupon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}