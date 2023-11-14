package com.qiong.handshaker.view.product.inner;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerRestock {
    private Date restock_date;
    private BigDecimal restock_price;
    private BigDecimal lowest_price;
    private BigDecimal selling_price;
    private Integer quantity;
    private String supplier_name;

    List<ViewInnerRestockDistribute> restock_distribute;
}
