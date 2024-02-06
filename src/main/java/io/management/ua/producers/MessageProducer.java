package io.management.ua.producers;

import io.management.ua.amqp.KafkaTopic;
import io.management.ua.amqp.messages.MessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, MessageModel> kafkaTemplate;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000L))
    public void produce(MessageModel messageModel) {
        kafkaTemplate.send(KafkaTopic.MESSAGE_TOPIC, messageModel);
    }
}
