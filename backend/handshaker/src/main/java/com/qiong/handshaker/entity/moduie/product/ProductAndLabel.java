package com.qiong.handshaker.entity.moduie.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("prod_product_and_label")
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndLabel extends BaseEntity {

    private Long product_sql_id;
    private Long label_sql_id;

    @TableField(exist = false)
    private Product product;

    @TableField(exist = false)
    private Label label;

    /*
    public ProductAndLabel(Long pid, Long lid) {
        this.setProduct_sql_id(pid);
        this.setLabel_sql_id(lid);
        autoGenerate(this);
    }
    public static List<ProductAndLabel> initMany(Long pid, List<Long> lids) {
        return lids.stream().map(lid -> new ProductAndLabel(pid, lid)).collect(Collectors.toList());
    }
    */
}
