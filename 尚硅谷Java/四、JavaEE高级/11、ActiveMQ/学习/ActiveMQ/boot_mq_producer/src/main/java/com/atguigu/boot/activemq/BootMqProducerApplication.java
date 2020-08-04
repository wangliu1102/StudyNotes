package com.atguigu.boot.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 是否开启定时任务调度功能
@EnableScheduling
@SpringBootApplication
public class BootMqProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMqProducerApplication.class, args);
    }

}
