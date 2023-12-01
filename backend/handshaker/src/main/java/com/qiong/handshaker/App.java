package com.qiong.handshaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// scanBasePackages = "com.qiong.handshaker"
@SpringBootApplication(scanBasePackages = "com.qiong")
@EnableTransactionManagement
public class App {

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}