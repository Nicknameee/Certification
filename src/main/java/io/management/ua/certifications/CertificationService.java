package io.management.ua.certifications;

import io.management.ua.amqp.models.users.UserApprovalRequest;
import io.management.ua.api.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final SmsService smsService;
    private static final Map<String, UUID> certificationCodes = new ConcurrentHashMap<>();
    public void process(UserApprovalRequest userApprovalRequest) {
        UUID certificationCode = UUID.randomUUID();

        certificationCodes.put(userApprovalRequest.getEmail(), certificationCode);


    }
}
