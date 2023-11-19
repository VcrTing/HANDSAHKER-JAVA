package com.qiong.handshaker.vo.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoBrokenOperaForm {

    @NotNull(message = "壞貨日期不为空")
    private Date date;

    private String remarks;

    @NotNull(message = "壞貨數量不为空")
    private Integer quantity;

    @NotNull(message = "壞貨产品不为空")
    private Long product_id;

    @NotNull(message = "壞貨樣式不为空")
    private Long variation;

    @NotNull(message = "壞貨倉庫不为空")
    private Long storehouse_id;


}
