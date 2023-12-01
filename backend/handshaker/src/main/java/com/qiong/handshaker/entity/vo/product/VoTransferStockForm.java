package com.qiong.handshaker.entity.vo.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoTransferStockForm {
    @NotNull(message = "調貨數量不為空")
    private Integer quantity;
    @NotNull(message = "調貨樣式不為空")
    private Long variation;
    @NotNull(message = "入貨倉庫不為空")
    private Long storehouse_to;
    @NotNull(message = "調貨倉庫不為空")
    private Long storehouse_from;
}
