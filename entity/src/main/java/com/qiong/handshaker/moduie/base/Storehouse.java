package com.qiong.handshaker.moduie.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@TableName("base_storehouse")
@NoArgsConstructor
@AllArgsConstructor
public class Storehouse extends BaseEntity {

    @NotNull(message = "倉庫名稱不為空")
    @Length(min = 2, max = 120, message = "倉庫名稱长度介于 {min} - {max} 之间")
    private String name;

    @NotNull(message = "聯絡電話不為空")
    @TableField(value = "phone_no")
    @Length(min = 4, max = 11, message = "电话号码长度介于 {min} - {max} 之间")
    private String phone_no;

    @Length(min = 0, max = 250, message = "關注/FOLLOW 值的长度介于 {min} - {max} 之间")
    private String facebook;

    @NotNull(message = "聯絡人姓名不為空")
    @TableField(value = "contact_person")
    @Length(min = 2, max = 120, message = "聯絡人姓名长度介于 {min} - {max} 之间")
    private String contact_person;

    @Length(min = 0, max = 250, message = "地址长度介于 {min} - {max} 之间")
    private String address;

    private String remark;
}
