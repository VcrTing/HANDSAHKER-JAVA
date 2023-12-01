package com.qiong.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qiong.demo")
public class App {

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}