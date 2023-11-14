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

    public static MemberLevel init(VoMemberLevelForm levelForm) {
        MemberLevel level = new MemberLevel();
        if (levelForm.getId() != null) level.setId(level.getId());
        level.setName(levelForm.getName());
        level.setDiscount(levelForm.getDiscount());
        //
        autoGenerate(level);
        return level;
    }
}
