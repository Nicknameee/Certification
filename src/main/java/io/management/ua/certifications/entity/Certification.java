package io.management.ua.certifications.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@RedisHash("certification")
public class Certification {
    @Id
    private String identifier;
    private String code;
    private ZonedDateTime issuedAt;
    private ZonedDateTime expiringDate;
}
