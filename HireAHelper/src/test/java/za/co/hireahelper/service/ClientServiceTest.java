/* ClientServiceTest.java
   Author: S Hendricks(221095136)
   Date: 18 May 2025
*/

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.factory.ClientFactory;
import za.co.hireahelper.repository.AreaRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClientServiceTest {

    @Autowired
    private ClientService service;

    @Autowired
    private AreaRepository areaRepository;

    private static Client client;

    static {
        List<Booking> bookings = new ArrayList<>();
        List<Message> messages = new ArrayList<>();

        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Cape Town Central")
                .build();

        client = ClientFactory.createClient(
                "user001",
                "Amina",
                "amina@example.com",
                "password123",
                "0823456789",
                area,
                bookings,
                messages
        );
    }

    @Test
    void a_create() {
        // Save the area first to avoid foreign key constraint failure
        areaRepository.save(client.getArea());

        Client created = service.create(client);
        assertNotNull(created);
        assertEquals(client.getUserId(), created.getUserId());
        System.out.println("Created Client: " + created);
    }

    @Test
    void b_read() {
        Client read = service.read(client.getUserId());
        assertNotNull(read);
        System.out.println("Read Client: " + read);
    }

    @Test
    void c_update() {
        Client updatedClient = new Client.Builder()
                .copy(client)
                .setEmail("amina.updated@example.com")
                .build();

        Client updated = service.update(updatedClient);
        assertNotNull(updated);
        assertEquals("amina.updated@example.com", updated.getEmail());
        System.out.println("Updated Client: " + updated);
    }

    @Test
    void d_getAll() {
        List<Client> clients = service.getAll();
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
        System.out.println("All Clients:");
        clients.forEach(System.out::println);
    }

    @Test
    void e_delete() {
        boolean deleted = service.delete(client.getUserId());
        assertTrue(deleted);
        System.out.println("Deleted Client with userId: " + client.getUserId());
    }
}

