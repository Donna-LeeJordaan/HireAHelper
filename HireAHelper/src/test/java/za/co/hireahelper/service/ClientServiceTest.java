/* ClientServiceTest.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.factory.ClientFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ClientServiceTest {

    @Autowired
    private ClientService service;

    @Autowired
    private AreaService areaService;

    private static Client client;

    private static final Area genericArea = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private static final List bookings = new ArrayList<>();
    private static final List messages = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        client = ClientFactory.createClient(
                "area001",
                "Amina",
                "amina@example.com",
                "password123",
                "0823456789",
                genericArea,
                bookings,
                messages
        );
        assertNotNull(client, "Client creation failed in setup");
    }

    @BeforeEach
    public void persistArea() {
        if (areaService.read(genericArea.getAreaId()) == null) {
            areaService.create(genericArea);
        }
        // Ensures the Area entity with ID "area001" is saved in the database before each test runs,
        // so that any Client referencing this Area can be persisted without errors.
    }

    @Test
    void a_create() {
        Client created = service.create(client);
        assertNotNull(created);
        assertEquals(client.getUserId(), created.getUserId());
        System.out.println("Created: " + created);
    }

    @Test
    @Transactional
    void b_read() {
        Client read = service.read(client.getUserId());
        assertNotNull(read);
        assertEquals(client.getUserId(), read.getUserId());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Client updated = new Client.Builder()
                .copy(client)
                .setEmail("updated@example.com")
                .build();

        Client result = service.update(updated);
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        System.out.println("Updated: " + result);
    }

    @Test
    @Transactional
    void d_getAll() {
        List<Client> all = service.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All Clients: " + all);
    }

    @Test
    void e_delete() {
        boolean success = service.delete(client.getUserId());
        assertTrue(success);
        System.out.println("Deleted: " + success);
    }
}
