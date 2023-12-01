package com.qiong.handshaker.moduie;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "created_at")
    private Date createdAt;

    // 邏輯刪除
    @TableField(select = false, fill = FieldFill.DEFAULT)
    @TableLogic(value = "1", delval = "0")
    private Integer status;

    public static  <T extends BaseEntity> T autoGenerate(T src) {
        src.setCreatedAt(new Date());
        src.setStatus(1);
        return src;
    }
}
