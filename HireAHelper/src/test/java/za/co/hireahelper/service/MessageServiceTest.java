// Gabriel Kiewietz
// 11 July 2025
// 230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.MessageFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServiceTest {

    @Autowired
    private MessageService service;

    private static Message message;

    @BeforeAll
    static void setup() {
        Client client = new Client.Builder().setUserId("CL001").build();
        ServiceProvider provider = new ServiceProvider.Builder().setUserId("SP001").build();

        message = MessageFactory.createMessage(
                "MSG001",
                LocalDateTime.now(),
                "Test message",
                client,
                provider
        );
    }

    @Test
    @Order(1)
    void a_create() {
        Message created = service.create(message);
        assertNotNull(created);
        assertEquals("MSG001", created.getMessageId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Message found = service.read(message.getMessageId());
        assertNotNull(found);
        System.out.println("Read: " + found);
    }

    @Test
    @Order(3)
    void c_update() {
        Message updatedMessage = new Message.Builder()
                .copy(message)
                .setContent("Updated test message")
                .build();

        Message updated = service.update(updatedMessage);
        assertNotNull(updated);
        assertEquals("Updated test message", updated.getContent());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_getAll() {
        List<Message> allMessages = service.getAll();
        assertNotNull(allMessages);
        assertFalse(allMessages.isEmpty());
        System.out.println("All messages: " + allMessages);
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = service.delete(message.getMessageId());
        assertTrue(deleted);
        System.out.println("Deleted: " + message.getMessageId());
    }
}
