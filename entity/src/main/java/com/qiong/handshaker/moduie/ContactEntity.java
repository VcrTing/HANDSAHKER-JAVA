package com.qiong.handshaker.moduie;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;


public class ContactEntity {

    protected String name;
    @Email
    @Length(min = 2, max = 250, message = "郵箱长度介于 {min} - {max} 之间")
    protected String email;
    @TableField(value = "phone_no")
    protected String phone_no;
}
