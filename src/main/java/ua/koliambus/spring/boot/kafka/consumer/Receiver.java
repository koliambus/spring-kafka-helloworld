package ua.koliambus.spring.boot.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.koliambus.spring.boot.kafka.model.ChatMessage;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger latch = new AtomicInteger();

    public void clearLatch() {
        latch.set(0);
    }

    public int getLatch() {
        return latch.get();
    }

    @KafkaListener(topics = "${kafka.topic.helloworld}")
    public void receiveHelloworld(String payload) {
        LOGGER.info("receiveHelloworld payload='{}'", payload);
        latch.incrementAndGet();
    }

    @KafkaListener(topics = "${kafka.topic.chat.message}")
    public void receiveChatMessage(ChatMessage payload) {
        LOGGER.info("receiveChatMessage payload='{}'", payload);
        latch.incrementAndGet();
    }

    @KafkaListener(topics = "${kafka.topic.chat.user.add}")
    public void receiveAddUser(ChatMessage payload) {
        LOGGER.info("receiveAddUser payload='{}'", payload);
        latch.incrementAndGet();
    }

    @KafkaListener(topics = "${kafka.topic.chat.user.leave}")
    public void receiveLeaveUser(ChatMessage payload) {
        LOGGER.info("receiveLeaveUser payload='{}'", payload);
        latch.incrementAndGet();
    }
}
