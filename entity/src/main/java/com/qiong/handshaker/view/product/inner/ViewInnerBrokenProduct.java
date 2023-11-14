package com.qiong.handshaker.view.product.inner;

import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerBrokenProduct {
    private Integer quantity;
    private Date date;
    private String remarks;

    private Object variation;
    private Storehouse storehouse;
}
