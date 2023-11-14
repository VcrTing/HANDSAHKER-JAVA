package com.qiong.handshaker.vo.product;

import com.qiong.handshaker.vo.product.inner.VoInnerRestockDistribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRestockPostForm {

    private Integer quantity;
    private Long product;
    private Long supplier;

    private List<VoInnerRestockDistribute>restock_distribute;

    private Long product_sql_id;
    private Long supplier_sql_id;

    private Date restock_date;

    private BigDecimal restock_price;
    private BigDecimal lowest_price;
    private BigDecimal selling_price;
}
