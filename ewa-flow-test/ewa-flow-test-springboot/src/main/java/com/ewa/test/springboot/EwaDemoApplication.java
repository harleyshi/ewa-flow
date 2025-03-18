package com.ewa.test.springboot;

import com.ewa.operator.OperatorsScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OperatorsScan
@SpringBootApplication
public class EwaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwaDemoApplication.class, args);
    }

}