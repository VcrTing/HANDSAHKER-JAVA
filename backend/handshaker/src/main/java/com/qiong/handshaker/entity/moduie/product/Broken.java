package com.qiong.handshaker.entity.moduie.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.vo.product.VoBrokenOperaForm;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import com.qiong.handshaker.entity.moduie.base.Storehouse;
import com.qiong.handshaker.utils.utils.bean.QBeanUntil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("prod_broken")
@NoArgsConstructor
@AllArgsConstructor
public class Broken extends BaseEntity {

    @TableField(value = "date")
    private Date date;

    private Integer quantity;

    private String remarks;

    @TableField(value = "storehouse_sql_id")
    private Long storehouse_sql_id;

    @TableField(value = "product_sql_id")
    private Long product_sql_id;

    @TableField(value = "variation_sql_id")
    private Long variation_sql_id;

    @TableField(exist = false)
    private Storehouse storehouse;

    @TableField(exist = false)
    private Variation variation;

    @TableField(exist = false)
    private Product product;

    public static Broken init(VoBrokenOperaForm form) {

        Broken res = QBeanUntil.convert(form, Broken.class);
        if (res == null) return null;

        res.setStorehouse_sql_id(form.getStorehouse_id());
        res.setProduct_sql_id(form.getProduct_id());
        res.setVariation_sql_id(form.getVariation());
        return res;
    }

    public Integer mustGetQuantity() { return (quantity == null) ? 0 : Math.abs(quantity); }
}
