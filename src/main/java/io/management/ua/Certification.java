package io.management.ua;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Certification {
    private final AbstractEnvironment environment;

    public static void main(String[] args) {
        log.debug("Certification service started");
        SpringApplication.run(Certification.class);
    }

    @PostConstruct
    public void showConnections() {
        System.out.println("Connecting to Redis: " + environment.getProperty("spring.redis.host") + ":" + environment.getProperty("spring.redis.port") + "/" + environment.getProperty("spring.redis.database"));
        System.out.println("Connecting to Apache Kafka Broker: " + environment.getProperty("spring.kafka.bootstrap-servers"));
    }
}