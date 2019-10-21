package com.kv.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SampleBlogAppApplication {
    private static final Logger log = LoggerFactory.getLogger(SampleBlogAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SampleBlogAppApplication.class, args);
        log.info("Initialized the Blog App");
    }
}
