package io.management.ua.producers;

import io.management.ua.amqp.KafkaTemplateTool;
import io.management.ua.amqp.models.KafkaTopic;
import io.management.ua.amqp.models.Message;
import io.management.ua.amqp.models.messages.MessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplateTool kafkaTemplate;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000L))
    public void produce(MessageModel messageModel) {
        kafkaTemplate.send(KafkaTopic.MESSAGE_TOPIC, new Message(messageModel));
    }
}
