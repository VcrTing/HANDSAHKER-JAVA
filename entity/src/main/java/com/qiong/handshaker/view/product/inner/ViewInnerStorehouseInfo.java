package com.qiong.handshaker.view.product.inner;

import com.qiong.handshaker.moduie.product.Variation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInnerStorehouseInfo {
    private Integer storehouse_id;
    private String storehouse_name;
    private String phone_no;
    private String storehouse_address;

    private Variation variation;
}
