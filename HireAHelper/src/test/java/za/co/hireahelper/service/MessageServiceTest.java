// Gabriel Kiewietz
// 11 July 2025
// 230990703

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.ClientFactory;
import za.co.hireahelper.factory.MessageFactory;
import za.co.hireahelper.factory.ServiceProviderFactory;
import za.co.hireahelper.repository.ServiceTypeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService serviceProviderService;

    private static Message message;

    private static ServiceType gardener;

    private static Area genericArea;

    private static Client client;

    private static ServiceProvider provider;

    @BeforeAll
    static void setup(
            @Autowired ServiceTypeRepository serviceTypeRepository,
            @Autowired AreaService areaService,
            @Autowired ClientService clientService,
            @Autowired ServiceProviderService serviceProviderService) {

        gardener = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeRepository.save(gardener);

        genericArea = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();
        areaService.create(genericArea);

        client = ClientFactory.createClient(
                "user001",
                "Amina",
                "amina@example.com",
                "password123",
                "0823456789",
                genericArea,
                new ArrayList<>(),    // Passing empty lists for bookings, messages, and reviews
                new ArrayList<>(),    // These represent related collections for the client/service provider
                new ArrayList<>()     // Initializing them as empty prevents null errors when accessing these lists later
        );
        clientService.create(client);

        provider = ServiceProviderFactory.createServiceProvider(
                "user007",
                "Tauriq Osman",
                "tauriqosman@gmail.com",
                "Tauriq04",
                "0611234567",
                genericArea,
                "tauriq.jpeg",
                "Skilled Gardener with 15 years experience",
                350.0,
                gardener,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        serviceProviderService.create(provider);

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
        Message created = messageService.create(message);
        assertNotNull(created);
        assertEquals("MSG001", created.getMessageId());
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Message found = messageService.read(message.getMessageId());
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

        Message updated = messageService.update(updatedMessage);
        assertNotNull(updated);
        assertEquals("Updated test message", updated.getContent());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_getAll() {
        List<Message> allMessages = messageService.getAll();
        assertNotNull(allMessages);
        assertFalse(allMessages.isEmpty());
        System.out.println("All messages: " + allMessages);
    }

    @Test
    @Order(5)
    void e_delete() {
        boolean deleted = messageService.delete(message.getMessageId());
        assertTrue(deleted);
        System.out.println("Deleted: " + message.getMessageId());
    }
}