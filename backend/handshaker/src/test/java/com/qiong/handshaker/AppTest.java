package com.qiong.handshaker;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qiong.handshaker.moduie.sys.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Objects;

public class AppTest {

    @Test
    public void nullTest() {
        Object a = null;

        if (a instanceof String) {
            System.out.println("非空");
        }
        if (a == null) {
            System.out.println("空");
        }

    }

    @Test
    public void code() {
        HttpStatus code = HttpStatus.resolve(400);
        System.out.println(code);
        System.out.println(code == null);
    }

    @Test
    public void json() throws JsonProcessingException {
        /*
        QToken qToken = new QToken();
        qToken.setAuthUserString("AAAAA");
        qToken.setUserId(1L);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(qToken);
        System.out.println(s);


        String src = "{\"jwt\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJxaW9uZ0AxNjMuY29tIiwiZXhwIjoxNjk5NzAzOTU2fQ.EF3ybNFewB6t0lKRnyrJ4SEql9C8fIUH74mt8AUi0KU\",\"user\":{\"id\":1,\"createdAt\":\"2023-11-07 22:13:16\",\"status\":null,\"username\":\"qiong@163.com\",\"email\":\"qiong@163.com\",\"password\":\"\",\"storehouseId\":1,\"isAdmin\":1,\"storehouse\":null},\"enabled\":true,\"password\":\"\",\"id\":1,\"username\":\"qiong@163.com\",\"authorities\":null,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}\n";

        AuthUser ai = mapper.readValue(src, AuthUser.class);
        System.out.println(ai);
        System.out.println(ai.getUser());
        */
    }

    @Test
    public void jackson() throws JsonProcessingException {
        String src = "{\"id\":1,\"createdAt\":\"2023-11-07 22:13:16\",\"status\":null,\"username\":\"qiong@163.com\",\"email\":\"qiong@163.com\",\"password\":\"\",\"storehouseId\":1,\"isAdmin\":1,\"storehouse\":null}";
        JSONObject jso = JSONUtil.parseObj(src);
        System.out.println(jso.get("username"));

        Object d = jso.get("createdAt");
        if (d instanceof Date) {
            System.out.println("日期类型");
        }
        System.out.println(d);

        User user = JSONUtil.toBean(src, User.class);
        System.out.println(user);
        System.out.println(user.getCreatedAt() instanceof Date);
        /*
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(src);
        User res = mapper.readValue(src, User.class);
        System.out.println(res);
         */
    }
}
