/* BookingFactoryTest.java

   Author: D.Jordaan (230613152)

   Date: 19 May 2025 */

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingFactoryTest {

    private static Client client1 = new Client.Builder()
            .setUserId("C1")
            .setName("John Doe")
            .setEmail("john.doe@example.com")
            .setPassword("password123")
            .setMobileNumber("0712345678")
            .build();

    private static ServiceProvider serviceProvider1 = new ServiceProvider.Builder()
            .setUserId("SP1")
            .setName("Jane Smith")
            .setEmail("jane.smith@example.com")
            .setPassword("smith123")
            .setMobileNumber("0823456789")
            .setProfileImage("jane.jpeg")
            .setDescription("Experienced service provider")
            .setRate(300)
            .build();

    private static Date futureDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
    private static Date pastDate = new Date(System.currentTimeMillis() - 86400000); // Yesterday

    private static Booking booking1 = BookingFactory.Createbooking(
            "B1", futureDate, "Confirmed", "Need tools to be provided", client1, serviceProvider1);

    private static Booking booking2 = BookingFactory.Createbooking(
            "B2", futureDate, "Pending", "Flexible on time", client1, serviceProvider1);

    @Test
    @Order(1)
    public void testCreateBooking() {
        assertNotNull(booking1);
        System.out.println(booking1);
    }

    @Test
    @Order(2)
    public void testCreateBookingWithAllAttributes() {
        assertNotNull(booking2);
        System.out.println(booking2);
    }

    @Test
    @Order(3)
    public void testCreateBookingWithPastDate() {
        Booking booking3 = BookingFactory.Createbooking(
                "B3", pastDate, "Confirmed", "Urgent service needed", client1, serviceProvider1);
        assertNull(booking3);
        System.out.println(booking3);
    }

    @Test
    @Order(4)
    public void testCreateBookingWithInvalidStatus() {
        Booking booking4 = BookingFactory.Createbooking(
                "B4", futureDate, "InvalidStatus", "Regular service", client1, serviceProvider1);
        assertNull(booking4);
        System.out.println(booking4);
    }

    @Test
    @Order(5)
    public void testCreateBookingWithNullFields() {
        Booking booking5 = BookingFactory.Createbooking(
                null, null, null, null, null, null);
        assertNull(booking5);
        System.out.println(booking5);
    }
}