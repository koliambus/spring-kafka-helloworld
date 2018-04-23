package ua.koliambus.spring.boot.kafka;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.koliambus.spring.boot.kafka.consumer.Receiver;
import ua.koliambus.spring.boot.kafka.model.ChatMessage;
import ua.koliambus.spring.boot.kafka.producer.ChatSender;
import ua.koliambus.spring.boot.kafka.producer.Sender;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaApplicationTest {

    private static final String HELLOWORLD_TOPIC = "helloworld.t";
    private static final String CHAT_MESSAGE_TOPIC = "chat-message.t";
    private static final String ADD_USER_TOPIC = "add-user.t";
    private static final String LEAVE_USER_TOPIC = "leave-user.t";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, HELLOWORLD_TOPIC);

    @Autowired
    private Receiver receiver;

    @Autowired
    private Sender sender;

    @Autowired
    private ChatSender chatSender;

    @Before
    public void init(){
        receiver.clearLatch();
    }

    @Test
    public void testReceive() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            sender.send(HELLOWORLD_TOPIC, "Hello Spring Kafka " + i + "!");
        }

        Thread.sleep(3000);
        assertEquals(10, receiver.getLatch());
    }

    @Test
    public void testChat() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(ChatMessage.MessageType.CHAT);
            chatMessage.setSender("Me!");
            chatMessage.setContent("Hello Spring Kafka " + i + "!");
            chatSender.send(CHAT_MESSAGE_TOPIC, chatMessage);
        }

        Thread.sleep(3000);
        assertEquals(10, receiver.getLatch());
    }

    @Test
    public void testAddUser() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(ChatMessage.MessageType.JOIN);
            chatMessage.setSender("Me!");
            chatMessage.setContent("Hello Spring Kafka " + i + "!");
            chatSender.send(ADD_USER_TOPIC, chatMessage);
        }

        Thread.sleep(3000);
        assertEquals(10, receiver.getLatch());
    }

    @Test
    public void testLeaveUser() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(ChatMessage.MessageType.JOIN);
            chatMessage.setSender("Me!");
            chatMessage.setContent("Hello Spring Kafka " + i + "!");
            chatSender.send(LEAVE_USER_TOPIC, chatMessage);
        }

        Thread.sleep(3000);
        assertEquals(10, receiver.getLatch());
    }

}