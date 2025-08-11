// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class MessageFactoryTest {

    private static Area genericArea = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private static Client client = ClientFactory.createClient(
            "user001", "Amina", "amina@example.com", "password123", "0823456789",
            genericArea, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    private static ServiceType gardener = new ServiceType.Builder()
            .setTypeId("type02")
            .setTypeName("Gardener")
            .build();

    private static ServiceProvider provider = ServiceProviderFactory.createServiceProvider(
            "user007", "Tauriq Osman", "moegamattauriqosman@gmail.com", "Tauriq04", "0611234567",
            genericArea, "tauriq.jpeg", "Skilled Gardener with 15 years experience", 350.0,
            gardener, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @Test
    void createMessage_ValidInput_ReturnsMessage() {
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
        assertEquals(provider, message.getServiceProvider());
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