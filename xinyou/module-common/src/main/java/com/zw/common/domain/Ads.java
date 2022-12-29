package com.zw.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName mall_ads
 */
@TableName(value ="mall_ads")
@Data
public class Ads implements Serializable {
    /**
     * 广告id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 广告名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 跳转的时间
     */
    @TableField(value = "jump_time")
    private Integer jumpTime;

    /**
     * 广告图片路径
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 是否展示：0：不展示，1：展示
     */
    @TableField(value = "is_show")
    private Integer isShow;

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
        Ads other = (Ads) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            &&(this.getJumpTime() == null ? other.getJumpTime() == null : this.getJumpTime().equals(other.getJumpTime()))
            && (this.getImageUrl() == null ? other.getImageUrl() == null : this.getImageUrl().equals(other.getImageUrl()))
            && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getJumpTime() == null) ? 0 : getJumpTime().hashCode());
        result = prime * result + ((getImageUrl() == null) ? 0 : getImageUrl().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", jumpTime=").append(jumpTime);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", isShow=").append(isShow);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}