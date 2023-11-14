package com.qiong.handshaker.vo.order;


import com.qiong.handshaker.view.product.inner.ViewInnerRestockDistribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRestockPostForm {


    private Integer quantity;

    private Date restock_date;

    @Digits(integer = 999999999, fraction = 2, message = "入貨價錢数字异常")
    private BigDecimal restock_price;

    @Digits(integer = 999999999, fraction = 2, message = "最低價錢数字异常")
    private BigDecimal lowest_price;

    @Digits(integer = 999999999, fraction = 2, message = "售價数字异常")
    private BigDecimal selling_price;

    // 都是 ID
    private Long product;
    private Long supplier;

    // 仓库 数量 分配
    private List<ViewInnerRestockDistribute> restock_distribute;
}
