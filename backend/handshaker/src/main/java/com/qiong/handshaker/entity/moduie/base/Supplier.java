package com.qiong.handshaker.entity.moduie.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@TableName("base_supplier")
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntity {

    @NotNull(message = "编号不为空")
    @Length(min = 2, max = 30, message = "编号长度介于 {min} - {max} 之间")
    @TableField(value = "supplier_id", exist = true)
    private String supplier_id;

    @Email
    private String email;

    @Length(min = 2, max = 90, message = "名字长度介于 {min} - {max} 之间")
    private String name;

    @NotNull(message = "电话号码不为空")
    @Length(min = 4, max = 11, message = "电话号码长度介于 {min} - {max} 之间")
    @TableField(value = "phone_no")
    private String phone_no;

    private String remarks;

    @TableField(value = "create_date")
    private Date create_date;

    @TableField(value = "contact_person")
    private String contact_person;

    @Length(min = 0, max = 250, message = "公司地址长度介于 {min} - {max} 之间")
    @TableField("office_address")
    private String office_address;

    @Length(min = 0, max = 250, message = "工厂地址长度介于 {min} - {max} 之间")
    @TableField("factory_address")
    private String factory_address;
}
