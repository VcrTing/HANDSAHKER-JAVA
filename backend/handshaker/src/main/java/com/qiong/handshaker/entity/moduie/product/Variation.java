package com.qiong.handshaker.entity.moduie.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.entity.vo.product.VoVariationOperaForm;
import com.qiong.handshaker.entity.moduie.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("prod_variation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variation extends BaseEntity {

    private String name;

    public static Variation init(VoVariationOperaForm form) {
        Variation variation = new Variation();
        variation.setName(form.getName());
        return autoGenerate(variation);
    }
}
