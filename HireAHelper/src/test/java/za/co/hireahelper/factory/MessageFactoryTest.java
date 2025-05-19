package za.co.hireahelper.factory;

// Gabriel Kiewietz
// 230990703
// 18 May 2024

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MessageFactoryTest {
    @Test
    void testCreateValidMessage() {
        Message message = MessageFactory.createMessage("msg123", LocalDateTime.now(), "Hello!");
        assertNotNull(message);
        assertEquals("msg123", message.getMessageId());
        assertEquals("Hello!", message.getContent());
    }

    @Test
    void testCreateMessageWithNullValues() {
        Message message = MessageFactory.createMessage(null, LocalDateTime.now(), "Hello!");
        assertNull(message);
    }

    @Test
    void testCreateMessageWithEmptyContent() {
        Message message = MessageFactory.createMessage("msg123", LocalDateTime.now(), "");
        assertNull(message);
    }
}
