package io.management.ua;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Certification {
    public static void main(String[] args) {
        log.debug("Certification service started");
        SpringApplication.run(Certification.class);
    }
}