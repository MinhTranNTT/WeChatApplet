package com.zw.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName mall_user
 */
@TableName(value ="mall_user")
@Data
public class User implements Serializable {
    /**
     * 用户表的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户的openid
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 用户头像的存储地址
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户的昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 用户性别：0为男，1为女
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 用户的手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 用户的地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 用户登录时间
     */
    @TableField(value = "register_time")
    private Date registerTime;

    /**
     * 上次登录的时间
     */
    @TableField(value = "last_register_time")
    private Date lastRegisterTime;

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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getRegisterTime() == null ? other.getRegisterTime() == null : this.getRegisterTime().equals(other.getRegisterTime()))
            && (this.getLastRegisterTime() == null ? other.getLastRegisterTime() == null : this.getLastRegisterTime().equals(other.getLastRegisterTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getRegisterTime() == null) ? 0 : getRegisterTime().hashCode());
        result = prime * result + ((getLastRegisterTime() == null) ? 0 : getLastRegisterTime().hashCode());
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
        sb.append(", avatar=").append(avatar);
        sb.append(", nickname=").append(nickname);
        sb.append(", gender=").append(gender);
        sb.append(", phone=").append(phone);
        sb.append(", address=").append(address);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", lastRegisterTime=").append(lastRegisterTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}