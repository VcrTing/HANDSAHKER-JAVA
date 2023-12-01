package com.qiong.handshaker.entity.moduie.product.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestockDistribute {

    private Integer quantity;

    private Long variation;
    private Long storehouse;
}
