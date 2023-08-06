package io.management.ua.producers;

import io.management.ua.amqp.models.KafkaTopic;
import io.management.ua.amqp.models.Message;
import io.management.ua.utility.certification.models.CertificationResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationResultProducer {
    private final KafkaTemplate<String, Message<CertificationResultModel>> kafkaTemplate;

    public void produce(CertificationResultModel userApprovalMessage) {
        kafkaTemplate.send(KafkaTopic.USER_APPROVAL_TOPIC, new Message<>(userApprovalMessage));
    }
}
