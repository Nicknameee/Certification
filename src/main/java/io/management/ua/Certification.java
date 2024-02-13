package io.management.ua;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.management")
@Slf4j
@RequiredArgsConstructor
public class Certification {
    public static void main(String[] args) {
        log.debug("Certification service started");
        SpringApplication.run(Certification.class);
    }
}