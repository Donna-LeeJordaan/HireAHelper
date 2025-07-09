/* ClientServiceTest.java

   Author: S Hendricks (221095136)

   Date: 09 July 2025 */

package za.co.hireahelper.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.factory.ClientFactory;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ClientServiceTest {

    @Autowired
    private ClientService service;

    private static final Client client = ClientFactory.createClient(
            "client123",
            "Aalia Moosa",
            "aalia.moosa@example.com",
            "securePass123",
            "0712345678",
            Collections.emptyList(),
            Collections.emptyList()
    );

    @Test
    void a_create() {
        Client created = service.create(client);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Client read = service.read(client.getUserId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void d_update() {
        Client updatedClient = new Client.Builder()
                .copy(client)
                .setName("Aalia M. Updated")
                .build();

        Client updated = service.update(updatedClient);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    void e_getAll() {
        var allClients = service.getAll();
        assertNotNull(allClients);
        System.out.println("All clients: " + allClients);
    }

    @Test
    void f_delete() {
        boolean deleted = service.delete(client.getUserId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }
}
