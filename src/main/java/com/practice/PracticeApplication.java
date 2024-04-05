package com.practice;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@MapperScan("com.practice.mapper")
@EnableScheduling
@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {
        // 处理循环注入
        // SpringApplication springApplication = new SpringApplication(PracticeApplication.class);
        // springApplication.setAllowCircularReferences(Boolean.TRUE);
        SpringApplication.run(PracticeApplication.class, args);
    }

}
