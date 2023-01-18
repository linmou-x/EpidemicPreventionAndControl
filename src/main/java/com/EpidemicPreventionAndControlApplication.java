package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Charles
 */
@SpringBootApplication
@MapperScan("com.mapper")
public class EpidemicPreventionAndControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpidemicPreventionAndControlApplication.class, args);
    }

}
