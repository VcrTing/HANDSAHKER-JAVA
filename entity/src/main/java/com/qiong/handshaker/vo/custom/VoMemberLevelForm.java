package com.qiong.handshaker.vo.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoMemberLevelForm {

    @Min(value = 0, message = "ID 异常")
    private Long id;

    @NotNull
    @Length(min = 1, max = 60, message = "等级名称应该处于 {min} 和 {max} 之间")
    private String name;

    @NotNull
    @Digits(integer = 1, fraction = 2, message = "折扣格式是 0 - 1 之间的 数字，包括 1")
    @Range(min = 0, max = 1, message = "等级折扣数字应当在 {min} 和 {max} 之间")
    private BigDecimal discount;
}
