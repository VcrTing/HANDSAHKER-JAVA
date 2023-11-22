package com.qiong.handshaker.moduie.custom;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiong.handshaker.moduie.BaseEntity;
import com.qiong.handshaker.vo.custom.VoMemberLevelForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@TableName("custom_member_level")
@NoArgsConstructor
@AllArgsConstructor
public class MemberLevel extends BaseEntity {

    private String name;
    private BigDecimal discount;

    public static MemberLevel init(VoMemberLevelForm levelForm, Long id) {
        MemberLevel res = new MemberLevel();
        res.setDiscount(levelForm.getDiscount());
        res.setName(levelForm.getName());
        if (id != null) res.setId(id);
        return autoGenerate(res);
    }
}
