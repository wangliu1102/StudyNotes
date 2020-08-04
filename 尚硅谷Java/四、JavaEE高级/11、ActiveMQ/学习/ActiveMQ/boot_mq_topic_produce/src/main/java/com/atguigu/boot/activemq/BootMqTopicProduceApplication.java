package com.atguigu.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootMqTopicProduceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqTopicProduceApplication.class, args);
    }

}
