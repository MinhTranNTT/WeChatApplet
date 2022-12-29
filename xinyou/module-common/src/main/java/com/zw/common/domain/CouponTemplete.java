package com.zw.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName mall_coupon_templete
 */
@TableName(value ="mall_coupon_templete")
@Data
public class CouponTemplete implements Serializable {
    /**
     * 制定的优惠券的模板
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 优惠券类型：0：满减，1：折扣，2：随机
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 优惠券名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 名称备注
     */
    @TableField(value = "coupon_name_remark")
    private String couponNameRemark;

    /**
     * 优惠券图片
     */
    @TableField(value = "image")
    private String image;

    /**
     * 发放数量
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 已领取数量
     */
    @TableField(value = "lead_count")
    private Integer leadCount;

    /**
     * 已使用数量
     */
    @TableField(value = "used_count")
    private Integer usedCount;

    /**
     * 适用商品类型：0：全部商品可用，1：指定商品可用，2：指定商品不可用
     */
    @TableField(value = "goods_type")
    private Integer goodsType;

    /**
     * 适用商品的id
     */
    @TableField(value = "product_ids")
    private String productIds;

    /**
     * 使用门槛：0：无门槛，1：有门槛
     */
    @TableField(value = "has_use_limit")
    private Integer hasUseLimit;

    /**
     * 满多少元可使用：0代表无限制
     */
    @TableField(value = "at_least")
    private BigDecimal atLeast;

    /**
     * 发放面额，当type为0时需要添加
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 1=<discount<=9.9，当type为1时需要添加
     */
    @TableField(value = "discount")
    private BigDecimal discount;

    /**
     * 最多折扣金额，当type为1时可选择性添加
     */
    @TableField(value = "discount_limit")
    private BigDecimal discountLimit;

    /**
     * 最低金额，当type为2时需要添加
     */
    @TableField(value = "min_money")
    private BigDecimal minMoney;

    /**
     * 最大金额，当type为2时需要添加
     */
    @TableField(value = "max_money")
    private BigDecimal maxMoney;

    /**
     * 过期类型，0：时间范围过期，1：领取之日固定日期后过期,2-领取次日固定日期后过期
     */
    @TableField(value = "validity_type")
    private Integer validityType;

    /**
     * 使用开始日期，过期类型为0时必填
     */
    @TableField(value = "start_use_time")
    private Date startUseTime;

    /**
     * 使用结束日期，过期类型为0是必填
     */
    @TableField(value = "end_use_time")
    private Date endUseTime;

    /**
     * 当validity_type为1或者2时需要添加 领取之日起或者次日N天内有效
     */
    @TableField(value = "fixed_term")
    private Integer fixedTerm;

    /**
     * 是否无限制使用：0：否，1：是
     */
    @TableField(value = "whether_limitless")
    private Integer whetherLimitless;

    /**
     * 每人最大领取个数
     */
    @TableField(value = "max_fetch")
    private Integer maxFetch;

    /**
     * 优惠叠加 0：不限制，1：优惠券仅原价购买商品时可用
     */
    @TableField(value = "whether_forbid_preference")
    private Integer whetherForbidPreference;

    /**
     * 是否显示
     */
    @TableField(value = "whether_show")
    private Integer whetherShow;

    /**
     * 订单的优惠总金额
     */
    @TableField(value = "discount_order_money")
    private BigDecimal discountOrderMoney;

    /**
     * 是否禁止发放0：否，1：是
     */
    @TableField(value = "whether_forbidden")
    private Integer whetherForbidden;

    /**
     * 使用优惠券购买的商品数量
     */
    @TableField(value = "order_goods_num")
    private Integer orderGoodsNum;

    /**
     * 状态：0：进行中，1：已结束
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 有效日期结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

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
        CouponTemplete other = (CouponTemplete) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCouponNameRemark() == null ? other.getCouponNameRemark() == null : this.getCouponNameRemark().equals(other.getCouponNameRemark()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getLeadCount() == null ? other.getLeadCount() == null : this.getLeadCount().equals(other.getLeadCount()))
            && (this.getUsedCount() == null ? other.getUsedCount() == null : this.getUsedCount().equals(other.getUsedCount()))
            && (this.getGoodsType() == null ? other.getGoodsType() == null : this.getGoodsType().equals(other.getGoodsType()))
            && (this.getProductIds() == null ? other.getProductIds() == null : this.getProductIds().equals(other.getProductIds()))
            && (this.getHasUseLimit() == null ? other.getHasUseLimit() == null : this.getHasUseLimit().equals(other.getHasUseLimit()))
            && (this.getAtLeast() == null ? other.getAtLeast() == null : this.getAtLeast().equals(other.getAtLeast()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDiscountLimit() == null ? other.getDiscountLimit() == null : this.getDiscountLimit().equals(other.getDiscountLimit()))
            && (this.getMinMoney() == null ? other.getMinMoney() == null : this.getMinMoney().equals(other.getMinMoney()))
            && (this.getMaxMoney() == null ? other.getMaxMoney() == null : this.getMaxMoney().equals(other.getMaxMoney()))
            && (this.getValidityType() == null ? other.getValidityType() == null : this.getValidityType().equals(other.getValidityType()))
            && (this.getStartUseTime() == null ? other.getStartUseTime() == null : this.getStartUseTime().equals(other.getStartUseTime()))
            && (this.getEndUseTime() == null ? other.getEndUseTime() == null : this.getEndUseTime().equals(other.getEndUseTime()))
            && (this.getFixedTerm() == null ? other.getFixedTerm() == null : this.getFixedTerm().equals(other.getFixedTerm()))
            && (this.getWhetherLimitless() == null ? other.getWhetherLimitless() == null : this.getWhetherLimitless().equals(other.getWhetherLimitless()))
            && (this.getMaxFetch() == null ? other.getMaxFetch() == null : this.getMaxFetch().equals(other.getMaxFetch()))
            && (this.getWhetherForbidPreference() == null ? other.getWhetherForbidPreference() == null : this.getWhetherForbidPreference().equals(other.getWhetherForbidPreference()))
            && (this.getWhetherShow() == null ? other.getWhetherShow() == null : this.getWhetherShow().equals(other.getWhetherShow()))
            && (this.getDiscountOrderMoney() == null ? other.getDiscountOrderMoney() == null : this.getDiscountOrderMoney().equals(other.getDiscountOrderMoney()))
            && (this.getWhetherForbidden() == null ? other.getWhetherForbidden() == null : this.getWhetherForbidden().equals(other.getWhetherForbidden()))
            && (this.getOrderGoodsNum() == null ? other.getOrderGoodsNum() == null : this.getOrderGoodsNum().equals(other.getOrderGoodsNum()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCouponNameRemark() == null) ? 0 : getCouponNameRemark().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getLeadCount() == null) ? 0 : getLeadCount().hashCode());
        result = prime * result + ((getUsedCount() == null) ? 0 : getUsedCount().hashCode());
        result = prime * result + ((getGoodsType() == null) ? 0 : getGoodsType().hashCode());
        result = prime * result + ((getProductIds() == null) ? 0 : getProductIds().hashCode());
        result = prime * result + ((getHasUseLimit() == null) ? 0 : getHasUseLimit().hashCode());
        result = prime * result + ((getAtLeast() == null) ? 0 : getAtLeast().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDiscountLimit() == null) ? 0 : getDiscountLimit().hashCode());
        result = prime * result + ((getMinMoney() == null) ? 0 : getMinMoney().hashCode());
        result = prime * result + ((getMaxMoney() == null) ? 0 : getMaxMoney().hashCode());
        result = prime * result + ((getValidityType() == null) ? 0 : getValidityType().hashCode());
        result = prime * result + ((getStartUseTime() == null) ? 0 : getStartUseTime().hashCode());
        result = prime * result + ((getEndUseTime() == null) ? 0 : getEndUseTime().hashCode());
        result = prime * result + ((getFixedTerm() == null) ? 0 : getFixedTerm().hashCode());
        result = prime * result + ((getWhetherLimitless() == null) ? 0 : getWhetherLimitless().hashCode());
        result = prime * result + ((getMaxFetch() == null) ? 0 : getMaxFetch().hashCode());
        result = prime * result + ((getWhetherForbidPreference() == null) ? 0 : getWhetherForbidPreference().hashCode());
        result = prime * result + ((getWhetherShow() == null) ? 0 : getWhetherShow().hashCode());
        result = prime * result + ((getDiscountOrderMoney() == null) ? 0 : getDiscountOrderMoney().hashCode());
        result = prime * result + ((getWhetherForbidden() == null) ? 0 : getWhetherForbidden().hashCode());
        result = prime * result + ((getOrderGoodsNum() == null) ? 0 : getOrderGoodsNum().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", name=").append(name);
        sb.append(", couponNameRemark=").append(couponNameRemark);
        sb.append(", image=").append(image);
        sb.append(", count=").append(count);
        sb.append(", leadCount=").append(leadCount);
        sb.append(", usedCount=").append(usedCount);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", productIds=").append(productIds);
        sb.append(", hasUseLimit=").append(hasUseLimit);
        sb.append(", atLeast=").append(atLeast);
        sb.append(", money=").append(money);
        sb.append(", discount=").append(discount);
        sb.append(", discountLimit=").append(discountLimit);
        sb.append(", minMoney=").append(minMoney);
        sb.append(", maxMoney=").append(maxMoney);
        sb.append(", validityType=").append(validityType);
        sb.append(", startUseTime=").append(startUseTime);
        sb.append(", endUseTime=").append(endUseTime);
        sb.append(", fixedTerm=").append(fixedTerm);
        sb.append(", whetherLimitless=").append(whetherLimitless);
        sb.append(", maxFetch=").append(maxFetch);
        sb.append(", whetherForbidPreference=").append(whetherForbidPreference);
        sb.append(", whetherShow=").append(whetherShow);
        sb.append(", discountOrderMoney=").append(discountOrderMoney);
        sb.append(", whetherForbidden=").append(whetherForbidden);
        sb.append(", orderGoodsNum=").append(orderGoodsNum);
        sb.append(", status=").append(status);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}