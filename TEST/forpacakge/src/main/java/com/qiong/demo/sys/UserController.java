package com.qiong.demo.sys;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Object got() {
        User u = new User(1L, "劉以達", "qiong@163.com", new Date());
        return JSONUtil.toJsonStr(u);
    }

    @GetMapping("/users")
    public List<User> list() {
        return userService.list();
    }
}
