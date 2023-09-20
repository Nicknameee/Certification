package io.management.ua.consumers;

import io.management.ua.amqp.KafkaTopic;
import io.management.ua.amqp.messages.CertificationRequestModel;
import io.management.ua.certifications.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationRequestConsumer {
    private final CertificationService certificationService;

    @KafkaListener(topics = KafkaTopic.CERTIFICATION_REQUEST_TOPIC, groupId = KafkaTopic.CERTIFICATION_REQUEST_TOPIC)
    public void handle(@Payload CertificationRequestModel certificationRequestModel) {
        certificationService.process(certificationRequestModel);
    }
}
