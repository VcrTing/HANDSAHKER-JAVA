package com.qiong.handshaker.vo.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoUserOptionForm {

    private String username;

    private String password;

    @Email
    @Length(min = 2, max = 250, message = "郵箱长度介于 {min} - {max} 之间")
    private String email;

    @NotNull(message = "姓名不为空")
    @Length(min = 2, max = 60, message = "姓名长度介于 {min} - {max} 之间")
    private String name;

    @NotNull(message = "电话号码不为空")
    @Length(min = 4, max = 11, message = "电话号码长度介于 {min} - {max} 之间")
    private String phone_no;

    private Boolean isAdmin;

    @NotNull(message = "所属仓库不为空")
    private Long storehouse;
}
