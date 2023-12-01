package com.qiong.demo.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName("sys_user")
public class User implements Serializable {
    private Long id;
    private String name;
    private String email;
    private Date created_at;
}
