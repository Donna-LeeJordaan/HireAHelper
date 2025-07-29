//Gabriel Kiewietz
// 11 July 2025
//230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.MessageFactory;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
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
    }

    @Test
    @Order(2)
    void b_read() {
        Message found = service.read(message.getMessageId());
        assertNotNull(found);
    }
}