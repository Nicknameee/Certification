package io.management.ua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@Slf4j
@EnableRedisRepositories
public class Main {
    public static void main(String[] args) {
        log.debug("certification service started");
        SpringApplication.run(Main.class);
    }
}