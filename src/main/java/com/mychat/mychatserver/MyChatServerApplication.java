package com.mychat.mychatserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mychat.mychatserver.mapper")
public class MyChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyChatServerApplication.class, args);
    }

}
