package com.qiong.handshaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qiong.handshaker")
@EnableTransactionManagement(proxyTargetClass = true) // 啟用實現類做回滾的代理，而非接口
public class App {

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}