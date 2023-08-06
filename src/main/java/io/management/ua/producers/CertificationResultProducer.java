package io.management.ua.producers;

import io.management.ua.amqp.models.KafkaTopic;
import io.management.ua.amqp.models.Message;
import io.management.ua.amqp.models.users.UserApprovalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificationResultProducer {
    private final KafkaTemplate<String, Message<UserApprovalMessage>> kafkaTemplate;

    public void produce(UserApprovalMessage userApprovalMessage) {
        kafkaTemplate.send(KafkaTopic.USER_APPROVAL_TOPIC, new Message<>(userApprovalMessage));
    }
}
