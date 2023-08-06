package io.management.ua.consumers;

import io.management.ua.amqp.models.KafkaTopic;
import io.management.ua.amqp.models.Message;
import io.management.ua.certifications.service.CertificationService;
import io.management.ua.utility.certification.models.CertificationRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationRequestConsumer {
    private final CertificationService certificationService;

    @KafkaListener(topics = KafkaTopic.USER_APPROVAL_TOPIC, groupId = "${spring.application.name}")
    public void handle(@Payload Message<CertificationRequestModel> certificationRequestModel) {
        certificationService.process(certificationRequestModel.getData());
    }
}
