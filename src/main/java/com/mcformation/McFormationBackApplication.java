package com.mcformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class McFormationBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(McFormationBackApplication.class, args);
    }

}