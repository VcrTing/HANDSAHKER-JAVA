package com.qiong.handshaker.vo.product.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoInnerRestockDistribute implements Serializable {

    @NotNull(message = "仓库的选择结果 必须 要有")
    private Long storehouse;
    @NotNull(message = "样式的选择结果 必须 要有")
    private Long variation;
    @NotNull(message = "入货数量 必须 要有")
    private Integer quantity;

    public Integer mustGetQuantity() {
        return quantity != null ? quantity : 0;
    }
}
