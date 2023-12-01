package com.qiong.handshaker.vo.product;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiong.handshaker.moduie.product.Product;
import com.qiong.handshaker.moduie.product.inner.ProductRemark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoProductPatchForm {

    @Digits(integer = 999999999, fraction = 2, message = "最低價錢数字异常")
    private BigDecimal new_lowest_price;
    @Digits(integer = 999999999, fraction = 2, message = "售價錢数字异常")
    private BigDecimal new_selling_price;

    private Long id;

    @NotNull(message = "产品 ID 不为空")
    @Length(min = 1, max = 90, message = "产品 ID 应该处于 {min} 和 {max} 之间")
    private String product_id;

    @NotNull(message = "产品 创建日期 不为空")
    private Date create_date;

    @NotNull(message = "产品 名称 不为空")
    @Length(min = 1, max = 90, message = "产品名称应该处于 {min} 和 {max} 之间")
    private String name;

    private List<ProductRemark> remarks;

    public LambdaUpdateWrapper<Product> genUpdateWrapper(Long pid) {
        LambdaUpdateWrapper<Product> uw = new LambdaUpdateWrapper<>();
        uw.eq(Product::getId, pid);
        uw.set(StringUtils.hasText(name), Product::getName, name);
        uw.set(StringUtils.hasText(product_id), Product::getProduct_id, product_id);
        uw.set(create_date != null, Product::getCreate_date, create_date);
        uw.set(remarks != null, Product::getRemarks, JSONUtil.toJsonStr(remarks));

        uw.set(new_lowest_price != null, Product::getNew_lowest_price, new_lowest_price);
        uw.set(new_selling_price != null, Product::getNew_selling_price, new_selling_price);

        return uw;
    }
}
