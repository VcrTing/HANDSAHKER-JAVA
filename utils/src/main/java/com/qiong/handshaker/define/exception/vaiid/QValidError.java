package com.qiong.handshaker.define.exception.vaiid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QValidError implements Serializable {
    private String field;
    private String message;
}
