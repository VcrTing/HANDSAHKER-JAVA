package com.qiong.handshaker.moduie.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@TableName("prod_restock_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestockRecord extends BaseEntity {
    private Long product_sql_id;
    private Long supplier_sql_id;

    private Date restock_date;

    private BigDecimal restock_price;
    private BigDecimal lowest_price;
    private BigDecimal selling_price;

    private String restock_distribute;
}
