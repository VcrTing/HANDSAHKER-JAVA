package com.qiong.handshaker.entity.vo.order;


import com.qiong.handshaker.entity.view.product.inner.ViewInnerRestockDistribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRestockPostForm {

    @NotNull(message = "入貨數量不為空")
    @Min(value = 0, message = "入貨數量 异常")
    private Integer quantity;

    private Date restock_date;

    @Digits(integer = 999999999, fraction = 2, message = "入貨價錢数字异常")
    private BigDecimal restock_price;

    @Digits(integer = 999999999, fraction = 2, message = "最低價錢数字异常")
    private BigDecimal lowest_price;

    @Digits(integer = 999999999, fraction = 2, message = "售價数字异常")
    private BigDecimal selling_price;

    // 都是 ID
    @NotNull(message = "入貨產品未選擇")
    private Long product;

    private Long supplier;

    // 仓库 数量 分配
    private List<ViewInnerRestockDistribute> restock_distribute;
}
