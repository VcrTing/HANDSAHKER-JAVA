package com.qiong.handshaker.moduie.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@TableName("prod_variation_and_storehouse_and_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariationAndStorehouseAndProduct extends BaseEntity {

   //  @NotNull(message = "产品 ID 不为空")
    @TableField("product_sql_id")
    private Long product_sql_id;

    // @NotNull(message = "样式 ID 不为空")
    @TableField("variation_sql_id")
    private Long variation_sql_id;

    // @NotNull(message = "仓库 ID 不为空")
    @TableField("storehouse_sql_id")
    private Long storehouse_sql_id;

    // 产品 库存 数量，真正的 库存 存在 这里
    private Integer quantity;

    // 乐观锁
    private Integer version;

    //
    // @TableField(exist = false)
    // private Product product;

    @TableField(exist = false)
    private Variation variation;

    @TableField(exist = false)
    private Storehouse storehouse;

    public VariationAndStorehouseAndProduct(Long product_sql_id, Long variation_sql_id, Long storehouse_sql_id) {
        this.product_sql_id = product_sql_id;
        this.variation_sql_id = variation_sql_id;
        this.storehouse_sql_id = storehouse_sql_id;
        this.version = 1;
        this.quantity = 0;
        autoGenerate(this);
    }
}
