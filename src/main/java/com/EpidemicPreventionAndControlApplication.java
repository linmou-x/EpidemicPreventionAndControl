package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mapper")
public class EpidemicPreventionAndControlApplication {

    public static void main(String[] args) {
//        try {
//            SpringApplication.run(EpidemicPreventionAndControlApplication.class, args);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(EpidemicPreventionAndControlApplication.class, args);
    }

}
