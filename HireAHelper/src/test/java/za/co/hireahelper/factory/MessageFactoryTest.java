// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.domain.ServiceProvider;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    @Test
    void createMessage_ValidInput_ReturnsMessage() {
        // Arrange
        Client client = new Client.Builder().setUserId("CL001").build();
        ServiceProvider provider = new ServiceProvider.Builder().setUserId("SP001").build();


        Message message = MessageFactory.createMessage(
                "MSG001",
                LocalDateTime.now(),
                "Test message",
                client,
                provider
        );


        assertNotNull(message);
        assertEquals("MSG001", message.getMessageId());
        assertEquals(client, message.getClient());
    }

    @Test
    void createMessage_NullInput_ReturnsNull() {

        Message message = MessageFactory.createMessage(
                null,
                LocalDateTime.now(),
                "Test",
                null,
                null
        );



        assertNull(message);
    }
}