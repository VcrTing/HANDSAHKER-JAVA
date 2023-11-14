package com.qiong.handshaker.vo.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoUserLoginForm {

    private String identifier;
    private String password;
}
