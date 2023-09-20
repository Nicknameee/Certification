package io.management.ua.producers;

import io.management.ua.amqp.KafkaTopic;
import io.management.ua.amqp.messages.CertificationResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationResultProducer {
    private final KafkaTemplate<String, CertificationResultModel> kafkaTemplate;

    @Retryable(maxAttempts = 100, backoff = @Backoff(delay = 1000L))
    public void produce(CertificationResultModel userApprovalMessage) {
        kafkaTemplate.send(KafkaTopic.CERTIFICATION_RESPONSE_TOPIC, userApprovalMessage);
    }
}
