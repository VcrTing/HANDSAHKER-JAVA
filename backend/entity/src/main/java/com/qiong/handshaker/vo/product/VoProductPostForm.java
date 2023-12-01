package com.qiong.handshaker.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.product.Label;
import com.qiong.handshaker.moduie.product.inner.ProductRemark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoProductPostForm {

    @NotNull(message = "产品 ID 不为空")
    private String product_id;

    @NotNull(message = "产品 创建日期 不为空")
    private Date create_date;

    @NotNull(message = "产品 名称 不为空")
    @Length(min = 1, max = 90, message = "产品名称应该处于 {min} 和 {max} 之间")
    private String name;

    private List<Long> labels;

    private List<ProductRemark> remarks;
}
