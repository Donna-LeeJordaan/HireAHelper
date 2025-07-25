// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Message;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class MessageFactoryTest {

    @Test
    void testCreateValidMessage() {
        Message message = MessageFactory.createMessage("This is a message", LocalDateTime.now(), "Hello!");
        System.out.println("Created Message: " + message); // for Sanity
        assertNotNull(message);
        assertEquals("This is a message", message.getMessageId());
        assertEquals("Hello!", message.getContent());
    }

    @Test
    void testCreateMessageWithNullValues() {
        Message message = MessageFactory.createMessage(null, LocalDateTime.now(), "Hello!");
        System.out.println("Message with null ID: " + message); // for sanity
        assertNull(message);
    }

    @Test
    void testCreateMessageWithEmptyContent() {
        Message message = MessageFactory.createMessage("This is a message", LocalDateTime.now(), "");
        System.out.println("Message with empty content: " + message); // for Sanity
        assertNull(message);
    }
}
