//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.MessageFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class MessageServiceTest {

    @Autowired
    private MessageService service;

    private static final Message message = MessageFactory.createMessage(
            "msg001",
            LocalDateTime.now(),
            "This is a test message."
    );

    @Test
    void a_create() {
        Message created = service.create(message);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Message read = service.read(message.getMessageId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Message updatedMessage = new Message.Builder()
                .copy(message)
                .setContent("Updated content")
                .build();

        Message updated = service.update(updatedMessage);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    void d_getAll() {
        var allMessages = service.getAll();
        assertNotNull(allMessages);
        System.out.println("All messages: " + allMessages);
    }

    @Test
    void e_delete() {
        boolean deleted = service.delete(message.getMessageId());
        assertTrue(deleted);
        System.out.println("Deleted: " + true);
    }
}
