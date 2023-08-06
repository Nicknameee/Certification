package io.management.ua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.debug("certification service started");
        SpringApplication.run(Main.class);
    }
}