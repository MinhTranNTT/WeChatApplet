package com.zw.common.domain;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 *
 * @TableName mall_products
 */
@TableName(value ="mall_products", autoResultMap = true)
@Data
public class Products implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品的排序依据
     */
    @TableField(value = "products_order")
    private Integer productsOrder;

    /**
     * 商品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 该商品对应的类别id
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 商品详情的banner列表
     */
    @TableField(value = "banner_list",typeHandler = JacksonTypeHandler.class)
    private JSONArray bannerList;

    /**
     * 商品的详情图片
     */
    @TableField(value = "detail_list",typeHandler = JacksonTypeHandler.class)
    private JSONArray detailList;

    /**
     * 商品的描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 商品的海报图
     */
    @TableField(value = "product_poster")
    private String productPoster;

    /**
     * 启程集合地点（可以多个，用逗号隔开）
     */
    @TableField(value = "departure_meeting_point",typeHandler = JacksonTypeHandler.class)
    private JSONArray departureMeetingPoint;

    /**
     * 返程集合地点（可以多个，用逗号隔开）
     */
    @TableField(value = "return_meeting_point",typeHandler = JacksonTypeHandler.class)
    private JSONArray returnMeetingPoint;

    /**
     * 最小年龄
     */
    @TableField(value = "min_age")
    private Integer minAge;

    /**
     * 最大年龄
     */
    @TableField(value = "max_age")
    private Integer maxAge;

    /**
     * 活动所在地点
     */
    @TableField(value = "location")
    private String location;

    /**
     * 1表示活动正在进行，2表示活动已经结束
     */
    @TableField(value = "is_activate")
    private Integer isActivate;

    /**
     * 商品的批次列表
     */
    @TableField(exist = false)
    private List<Batch> batchList;

    /**
     * 商品的类别名称
     */
    @TableField(exist = false)
    private String categoryName;

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
        Products other = (Products) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductsOrder() == null ? other.getProductsOrder() == null : this.getProductsOrder().equals(other.getProductsOrder()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getBannerList() == null ? other.getBannerList() == null : this.getBannerList().equals(other.getBannerList()))
                && (this.getDetailList() == null ? other.getDetailList() == null : this.getDetailList().equals(other.getDetailList()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getProductPoster() == null ? other.getProductPoster() == null : this.getProductPoster().equals(other.getProductPoster()))
                && (this.getDepartureMeetingPoint() == null ? other.getDepartureMeetingPoint() == null : this.getDepartureMeetingPoint().equals(other.getDepartureMeetingPoint()))
                && (this.getReturnMeetingPoint() == null ? other.getReturnMeetingPoint() == null : this.getReturnMeetingPoint().equals(other.getReturnMeetingPoint()))
                && (this.getMinAge() == null ? other.getMinAge() == null : this.getMinAge().equals(other.getMinAge()))
                && (this.getMaxAge() == null ? other.getMaxAge() == null : this.getMaxAge().equals(other.getMaxAge()))
                && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
                && (this.getIsActivate() == null ? other.getIsActivate() == null : this.getIsActivate().equals(other.getIsActivate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductsOrder() == null) ? 0 : getProductsOrder().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getBannerList() == null) ? 0 : getBannerList().hashCode());
        result = prime * result + ((getDetailList() == null) ? 0 : getDetailList().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getProductPoster() == null) ? 0 : getProductPoster().hashCode());
        result = prime * result + ((getDepartureMeetingPoint() == null) ? 0 : getDepartureMeetingPoint().hashCode());
        result = prime * result + ((getReturnMeetingPoint() == null) ? 0 : getReturnMeetingPoint().hashCode());
        result = prime * result + ((getMinAge() == null) ? 0 : getMinAge().hashCode());
        result = prime * result + ((getMaxAge() == null) ? 0 : getMaxAge().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getIsActivate() == null) ? 0 : getIsActivate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productsOrder=").append(productsOrder);
        sb.append(", name=").append(name);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", price=").append(price);
        sb.append(", bannerList=").append(bannerList);
        sb.append(", detailList=").append(detailList);
        sb.append(", description=").append(description);
        sb.append(", productPoster=").append(productPoster);
        sb.append(", departureMeetingPoint=").append(departureMeetingPoint);
        sb.append(", returnMeetingPoint=").append(returnMeetingPoint);
        sb.append(", minAge=").append(minAge);
        sb.append(", maxAge=").append(maxAge);
        sb.append(", location=").append(location);
        sb.append(", isActivate=").append(isActivate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}