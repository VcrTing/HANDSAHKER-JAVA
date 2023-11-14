package com.qiong.handshaker.moduie.custom;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.define.enums.EnumSex;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.vo.custom.VoCustomOptionForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;


@Data
@TableName("custom_customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String name;

    @Email
    private String email;

    @TableField(value = "phone_no")
    private String phone_no;

    private String remarks;

    @TableField(value = "create_date")
    private Date create_date;

    private Date birthdate;

    private EnumSex sex;

    @TableField(value = "member_id")
    private String member_id;

    private String address;

    @TableField(value = "member_level_id")
    private Long member_level_id;

    @TableField(exist = false)
    private MemberLevel member_level;

    public static Customer init(VoCustomOptionForm form) {
        // 比较高级的 借助 JSON 转换

        Long agentId = form.getMember_level();
        form.setMember_level(null);

        Customer customer = JSONUtil.toBean(JSONUtil.toJsonStr(form), Customer.class);
        customer.setMember_level_id(agentId);

        return autoGenerate(customer);
    }
}
