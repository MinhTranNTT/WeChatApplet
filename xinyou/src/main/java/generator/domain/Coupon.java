package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 优惠券
 * @TableName mall_coupon
 */
@TableName(value ="mall_coupon")
@Data
public class Coupon implements Serializable {
    /**
     * 用户领取的优惠券的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 优惠券类型 0-满减 1-折扣 2-随机
     */
    @TableField(value = "coupon_type")
    private Integer couponType;

    /**
     * 优惠券名称
     */
    @TableField(value = "coupon_name")
    private String couponName;

    /**
     * 优惠券编码
     */
    @TableField(value = "coupon_code")
    private String couponCode;

    /**
     * 领用人openid
     */
    @TableField(value = "user_openid")
    private String userOpenid;

    /**
     * 优惠券使用订单sn
     */
    @TableField(value = "use_order_sn")
    private String useOrderSn;

    /**
     * 适用商品类型0-全部商品可用；1-指定商品可用；2-指定商品不可用
     */
    @TableField(value = "goods_type")
    private Object goodsType;

    /**
     * 适用商品id
     */
    @TableField(value = "goods_ids")
    private String goodsIds;

    /**
     * 最小金额
     */
    @TableField(value = "at_least")
    private BigDecimal atLeast;

    /**
     * 面额
     */
    @TableField(value = "money")
    private BigDecimal money;

    /**
     * 1 =< 折扣 <= 9.9 当type为1时需要添加
     */
    @TableField(value = "discount")
    private BigDecimal discount;

    /**
     * 最多折扣金额 当type为1时可选择性添加
     */
    @TableField(value = "discount_limit")
    private BigDecimal discountLimit;

    /**
     * 优惠叠加 0-不限制 1- 优惠券仅原价购买商品时可用
     */
    @TableField(value = "whether_forbid_preference")
    private Object whetherForbidPreference;

    /**
     * 优惠券状态 0：未使用 1已使用 2已过期
     */
    @TableField(value = "status")
    private Object status;

    /**
     * 获取方式 1：新人券 2.直接领取3.活动领取 4转赠 5分享获取
     */
    @TableField(value = "get_type")
    private Object getType;

    /**
     * 领取时间
     */
    @TableField(value = "fetch_time")
    private Date fetchTime;

    /**
     * 使用时间
     */
    @TableField(value = "use_time")
    private Date useTime;

    /**
     * 可使用的开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 有效期结束时间
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
        Coupon other = (Coupon) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCouponType() == null ? other.getCouponType() == null : this.getCouponType().equals(other.getCouponType()))
            && (this.getCouponName() == null ? other.getCouponName() == null : this.getCouponName().equals(other.getCouponName()))
            && (this.getCouponCode() == null ? other.getCouponCode() == null : this.getCouponCode().equals(other.getCouponCode()))
            && (this.getUserOpenid() == null ? other.getUserOpenid() == null : this.getUserOpenid().equals(other.getUserOpenid()))
            && (this.getUseOrderSn() == null ? other.getUseOrderSn() == null : this.getUseOrderSn().equals(other.getUseOrderSn()))
            && (this.getGoodsType() == null ? other.getGoodsType() == null : this.getGoodsType().equals(other.getGoodsType()))
            && (this.getGoodsIds() == null ? other.getGoodsIds() == null : this.getGoodsIds().equals(other.getGoodsIds()))
            && (this.getAtLeast() == null ? other.getAtLeast() == null : this.getAtLeast().equals(other.getAtLeast()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDiscountLimit() == null ? other.getDiscountLimit() == null : this.getDiscountLimit().equals(other.getDiscountLimit()))
            && (this.getWhetherForbidPreference() == null ? other.getWhetherForbidPreference() == null : this.getWhetherForbidPreference().equals(other.getWhetherForbidPreference()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGetType() == null ? other.getGetType() == null : this.getGetType().equals(other.getGetType()))
            && (this.getFetchTime() == null ? other.getFetchTime() == null : this.getFetchTime().equals(other.getFetchTime()))
            && (this.getUseTime() == null ? other.getUseTime() == null : this.getUseTime().equals(other.getUseTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCouponType() == null) ? 0 : getCouponType().hashCode());
        result = prime * result + ((getCouponName() == null) ? 0 : getCouponName().hashCode());
        result = prime * result + ((getCouponCode() == null) ? 0 : getCouponCode().hashCode());
        result = prime * result + ((getUserOpenid() == null) ? 0 : getUserOpenid().hashCode());
        result = prime * result + ((getUseOrderSn() == null) ? 0 : getUseOrderSn().hashCode());
        result = prime * result + ((getGoodsType() == null) ? 0 : getGoodsType().hashCode());
        result = prime * result + ((getGoodsIds() == null) ? 0 : getGoodsIds().hashCode());
        result = prime * result + ((getAtLeast() == null) ? 0 : getAtLeast().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDiscountLimit() == null) ? 0 : getDiscountLimit().hashCode());
        result = prime * result + ((getWhetherForbidPreference() == null) ? 0 : getWhetherForbidPreference().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGetType() == null) ? 0 : getGetType().hashCode());
        result = prime * result + ((getFetchTime() == null) ? 0 : getFetchTime().hashCode());
        result = prime * result + ((getUseTime() == null) ? 0 : getUseTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
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
        sb.append(", couponType=").append(couponType);
        sb.append(", couponName=").append(couponName);
        sb.append(", couponCode=").append(couponCode);
        sb.append(", userOpenid=").append(userOpenid);
        sb.append(", useOrderSn=").append(useOrderSn);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", goodsIds=").append(goodsIds);
        sb.append(", atLeast=").append(atLeast);
        sb.append(", money=").append(money);
        sb.append(", discount=").append(discount);
        sb.append(", discountLimit=").append(discountLimit);
        sb.append(", whetherForbidPreference=").append(whetherForbidPreference);
        sb.append(", status=").append(status);
        sb.append(", getType=").append(getType);
        sb.append(", fetchTime=").append(fetchTime);
        sb.append(", useTime=").append(useTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}