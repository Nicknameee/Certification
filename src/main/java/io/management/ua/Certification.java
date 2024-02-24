package io.management.ua;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "io.management")
@Slf4j
@RequiredArgsConstructor
@EnableEurekaClient
public class Certification {
    public static void main(String[] args) {
        SpringApplication.run(Certification.class);
        log.debug("Certification service started");
    }
}