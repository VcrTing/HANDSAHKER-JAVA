package com.qiong.handshaker.worker.security.dataset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("sys_token")
@NoArgsConstructor
@AllArgsConstructor
public class QToken {

    private Long id;

    @TableField("user_id")
    private Long userId;
    private String token;

    private Date expire;
    @TableField("auth_user_string")
    private String authUserString;

}
