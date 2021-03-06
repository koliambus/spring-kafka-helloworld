package ua.koliambus.spring.boot.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ua.koliambus.spring.boot.kafka.model.ChatMessage;

@Component
public class ChatSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatSender.class);

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public void send(String topic, ChatMessage payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}
