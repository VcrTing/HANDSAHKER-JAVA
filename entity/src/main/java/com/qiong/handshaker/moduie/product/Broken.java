package com.qiong.handshaker.moduie.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("prod_broken")
@NoArgsConstructor
@AllArgsConstructor
public class Broken extends BaseEntity {

    @TableField(value = "date")
    private Date date;

    private Integer quantity;

    private String remarks;

    @TableField(value = "storehouse_sql_id")
    private String storehouse_sql_id;

    @TableField(exist = false)
    private Storehouse storehouse;

    @TableField(value = "product_sql_id")
    private Long product_sql_id;

    @TableField(exist = false)
    private Product product;
}
