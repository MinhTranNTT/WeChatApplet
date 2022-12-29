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
 * @TableName mall_camper
 */
@TableName(value ="mall_camper")
@Data
public class Camper implements Serializable {
    /**
     * 营员表的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对应的用户的openid
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 营员的姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 营员的性别：0为男，1为女
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 用户生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 证件类型
     */
    @TableField(value = "type_of_certi")
    private Integer typeOfCerti;

    /**
     * 有效证件号
     */
    @TableField(value = "valid_id_number")
    private String validIdNumber;

    /**
     * 其他证件号
     */
    @TableField(value = "other_id_number")
    private String otherIdNumber;

    /**
     * 监护人姓名
     */
    @TableField(value = "guardian_name")
    private String guardianName;

    /**
     * 照片存储的路径
     */
    @TableField(value = "photo_url")
    private String photoUrl;

    /**
     * 营员在读学校
     */
    @TableField(value = "school")
    private String school;

    /**
     * 营员在读年纪
     */
    @TableField(value = "grade")
    private String grade;

    /**
     * 营员的衣服尺寸:0:110,1:120,以此类推
     */
    @TableField(value = "cloth_size")
    private Integer clothSize;

    /**
     * 监护人手机号
     */
    @TableField(value = "phone")
    private String phone;

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
        Camper other = (Camper) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
                && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
                && (this.getTypeOfCerti() == null ? other.getTypeOfCerti() == null : this.getTypeOfCerti().equals(other.getTypeOfCerti()))
                && (this.getValidIdNumber() == null ? other.getValidIdNumber() == null : this.getValidIdNumber().equals(other.getValidIdNumber()))
                && (this.getOtherIdNumber() == null ? other.getOtherIdNumber() == null : this.getOtherIdNumber().equals(other.getOtherIdNumber()))
                && (this.getGuardianName() == null ? other.getGuardianName() == null : this.getGuardianName().equals(other.getGuardianName()))
                && (this.getPhotoUrl() == null ? other.getPhotoUrl() == null : this.getPhotoUrl().equals(other.getPhotoUrl()))
                && (this.getSchool() == null ? other.getSchool() == null : this.getSchool().equals(other.getSchool()))
                && (this.getGrade() == null ? other.getGrade() == null : this.getGrade().equals(other.getGrade()))
                && (this.getClothSize() == null ? other.getClothSize() == null : this.getClothSize().equals(other.getClothSize()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getTypeOfCerti() == null) ? 0 : getTypeOfCerti().hashCode());
        result = prime * result + ((getValidIdNumber() == null) ? 0 : getValidIdNumber().hashCode());
        result = prime * result + ((getOtherIdNumber() == null) ? 0 : getOtherIdNumber().hashCode());
        result = prime * result + ((getGuardianName() == null) ? 0 : getGuardianName().hashCode());
        result = prime * result + ((getPhotoUrl() == null) ? 0 : getPhotoUrl().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getGrade() == null) ? 0 : getGrade().hashCode());
        result = prime * result + ((getClothSize() == null) ? 0 : getClothSize().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", gender=").append(gender);
        sb.append(", birthday=").append(birthday);
        sb.append(", typeOfCerti=").append(typeOfCerti);
        sb.append(", validIdNumber=").append(validIdNumber);
        sb.append(", otherIdNumber=").append(otherIdNumber);
        sb.append(", guardianName=").append(guardianName);
        sb.append(", photoUrl=").append(photoUrl);
        sb.append(", school=").append(school);
        sb.append(", grade=").append(grade);
        sb.append(", clothSize=").append(clothSize);
        sb.append(", phone=").append(phone);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}