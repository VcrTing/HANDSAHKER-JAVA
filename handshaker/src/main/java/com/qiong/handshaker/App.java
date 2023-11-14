package com.qiong.handshaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// scanBasePackages = "com.qiong.handshaker"
@SpringBootApplication(scanBasePackages = "com.qiong")
public class App {

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}