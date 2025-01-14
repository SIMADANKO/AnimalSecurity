package com.animalSecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.animalSecurity.mapper")
public class AnimalSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalSecurityApplication.class, args);
    }

}
