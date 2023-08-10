package io.management.ua.producers;

import io.management.ua.amqp.KafkaTemplateTool;
import io.management.ua.amqp.models.KafkaTopic;
import io.management.ua.amqp.models.Message;
import io.management.ua.utility.certification.models.CertificationResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationResultProducer {
    private final KafkaTemplateTool kafkaTemplate;

    public void produce(CertificationResultModel userApprovalMessage) {
        kafkaTemplate.send(KafkaTopic.CERTIFICATION_RESPONSE_TOPIC, new Message(userApprovalMessage));
    }
}
