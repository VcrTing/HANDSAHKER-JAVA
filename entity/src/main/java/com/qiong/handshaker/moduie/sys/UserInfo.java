package com.qiong.handshaker.moduie.sys;

import com.qiong.handshaker.moduie.base.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private boolean isAdmin;

    private Storehouse storehouse;
}
