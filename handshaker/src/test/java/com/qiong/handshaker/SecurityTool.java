package com.qiong.handshaker;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityTool {

    @Test
    public void pass() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String res = encoder.encode("12345");
        System.out.println(res);
    }
}
