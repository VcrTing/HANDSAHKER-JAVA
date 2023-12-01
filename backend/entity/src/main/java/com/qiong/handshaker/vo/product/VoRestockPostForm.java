package com.qiong.handshaker.vo.product;

import com.qiong.handshaker.vo.product.inner.VoInnerRestockDistribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRestockPostForm {

    @NotNull(message = "产品数据 必须要有")
    private String product;

    private Long supplier;

    private List<VoInnerRestockDistribute> restock_distribute;

    @NotNull(message = "入货日期 必须要有")
    private String restock_date;

    @NotNull(message = "入货价格 必须要有")
    private BigDecimal restock_price;
    private BigDecimal lowest_price;
    private BigDecimal selling_price;

    public boolean hasSupplier() {
        return supplier != null;
    }
}
