package com.qiong.handshaker.moduie.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.vo.sys.VoUserOptionForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@TableName("sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String username;

    private String email;

    private String password;

    private String name;

    @TableField(value = "phone_no")
    private String phone_no;

    @TableField(value = "storehouse_id")
    private Long storehouse_id;

    @TableField(value = "is_admin")
    private Integer isAdmin;

    @TableField(exist = false)
    private Storehouse storehouse;

    /**
    * 只获取 脱敏信息
    * @params
    * @return
    */
    public void doProtected() {
        this.setPassword("");
    }

    /**
    * 来自于 VoUserOptionForm
    * @params
    * @return
    */
    public static User init(VoUserOptionForm form) {

        // 比较原始的 手写 转换

        User u = new User();

        u.setName(form.getName());
        u.setEmail(form.getEmail());
        u.setUsername(form.getEmail());
        u.setPassword(form.getPassword());
        u.setPhone_no(form.getPhone_no());
        u.setStorehouse_id(form.getStorehouse());

        u.setIsAdmin(form.getIsAdmin() ? 1 : 0);
        return autoGenerate(u);
    }

}
