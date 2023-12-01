package com.qiong.handshaker.entity.vo.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoLabelForm {

    @NotNull
    @Length(min = 1, max = 60, message = "标签名称应该处于 {min} 和 {max} 之间")
    private String name;

    private Boolean isShow;
}
