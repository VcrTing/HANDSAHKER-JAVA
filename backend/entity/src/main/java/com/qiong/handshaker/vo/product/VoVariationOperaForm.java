package com.qiong.handshaker.vo.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoVariationOperaForm {

    @NotNull(message = "标签名称不为空")
    @Length(min = 2, max = 60, message = "标签名称长度介于 {min} - {max} 之间")
    private String name;

    private Long product;
}
