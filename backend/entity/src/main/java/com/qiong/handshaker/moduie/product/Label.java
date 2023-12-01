package com.qiong.handshaker.moduie.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.vo.product.VoLabelForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@TableName("prod_label")
@NoArgsConstructor
@AllArgsConstructor
public class Label extends BaseEntity {

    private String name;

    @TableField(value = "is_show")
    private Integer isShow;

    @TableField(exist = false)
    private List<Product> products;

    public static Label init(VoLabelForm form) {
        Label label = new Label();
        label.setName(form.getName());
        label.setIsShow(form.getIsShow() ? 1 : 0);

        return autoGenerate(label);
    }
}
