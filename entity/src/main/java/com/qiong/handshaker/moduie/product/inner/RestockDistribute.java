package com.qiong.handshaker.moduie.product.inner;

import com.qiong.handshaker.moduie.base.Storehouse;
import com.qiong.handshaker.moduie.product.Variation;
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
