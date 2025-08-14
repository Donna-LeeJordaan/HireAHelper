/* ClientFactoryTest.java
   Author: S Hendricks(221095136)
   Date: 18 May 2025
*/

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientFactoryTest {

    private static List<Booking> bookings = new ArrayList<>();
    private static List<Message> messages = new ArrayList<>();

    // Generic placeholder Area for all tests
    private static Area genericArea = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private static Client c1 = ClientFactory.createClient(
            "user001", "Amina", "amina@example.com", "password123", "0823456789",
            genericArea, bookings, messages);

    private static Client c2 = ClientFactory.createClient(
            "user002", "Yusuf", "yusuf@example.com", "password456", "0834567890",
            genericArea, bookings, messages);

    private static Client c3 = ClientFactory.createClient(
            "user003", "Fatima", "fatima@example.com", "password789", "0845678901",
            genericArea, bookings, messages);

    @Test
    @Order(1)
    public void testCreateClient() {
        assertNotNull(c1);
        System.out.println(c1.toString());
    }

    @Test
    @Order(2)
    public void testCreateClientWithAllAttributes() {
        assertNotNull(c2);
        System.out.println(c2.toString());
    }

    @Test
    @Order(3)
    public void testCreateClientThatFailsInvalidEmail() {
        Client c4 = ClientFactory.createClient(
                "user004", "Imran", "imran-at-example.com", "password000", "0823456789",
                genericArea, bookings, messages);
        System.out.println("Client c4 = " + c4);
        assertNull(c4);
    }

    @Test
    @Order(4)
    public void testCreateClientThatFailsInvalidMobile() {
        Client c5 = ClientFactory.createClient(
                "user005", "Zainab", "zainab@example.com", "password111", "abc1234567",
                genericArea, bookings, messages);
        System.out.println("Client c5 = " + c5);
        assertNull(c5);
    }

    @Test
    @Order(5)
    public void testCreateClientThatFailsNullFields() {
        Client c6 = ClientFactory.createClient(
                null, "Rashid", "rashid@example.com", "password222", "0823456789",
                genericArea, bookings, messages);
        System.out.println("Client c6 = " + c6);
        assertNull(c6);
    }

    @Test
    @Order(6)
    @Disabled
    public void testNotImplementedYet() {
        // Todo: Add future test cases here
    }
}


