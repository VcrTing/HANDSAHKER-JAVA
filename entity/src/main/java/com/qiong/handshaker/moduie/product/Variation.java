package com.qiong.handshaker.moduie.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.vo.product.VoVariationOperaForm;
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
