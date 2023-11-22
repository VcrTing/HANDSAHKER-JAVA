package com.qiong.handshaker.vo.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoTransferStockForm {

    private Integer quantity;
    private Long variation;
    private Long storehouse_to;
    private Long storehouse_from;
}
