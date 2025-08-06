/* BookingControllerTest.java
   Author: D.Jordaan (230613152)
   Date: 25 July 2025 / modified on 6 August 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.BookingFactory;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookingControllerTest {

    private static Booking booking;
    private static Client client;
    private static ServiceProvider serviceProvider;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/HireAHelper/booking";

    @BeforeAll
    public static void setUp() {
        client = new Client.Builder()
                .setUserId("client123")
                .setName("Test Client")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp1")
                .setName("Test Provider")
                .build();

        booking = BookingFactory.createBooking(
                "booking123",
                new Date(System.currentTimeMillis() + 86400000), // Tomorrow
                "Confirmed",
                "Test notes",
                client,
                serviceProvider,
                null // Reviews can be added later
        );
    }

    @Test
    void a_create() {
        String url = BASE_URL;
        ResponseEntity<Booking> postResponse = restTemplate.postForEntity(url, booking, Booking.class);
        assertAll(
                () -> assertNotNull(postResponse),
                () -> assertNotNull(postResponse.getBody()),
                () -> assertEquals(booking.getBookingId(), postResponse.getBody().getBookingId())
        );
        System.out.println("Created: " + postResponse.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/" + booking.getBookingId();
        ResponseEntity<Booking> response = restTemplate.getForEntity(url, Booking.class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(booking.getBookingId(), response.getBody().getBookingId())
        );
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Completed")
                .setNotes("Updated notes")
                .build();

        String url = BASE_URL;
        restTemplate.put(url, updatedBooking);

        ResponseEntity<Booking> response = restTemplate.getForEntity(
                BASE_URL + "/" + updatedBooking.getBookingId(),
                Booking.class
        );
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertEquals("Completed", response.getBody().getStatus()),
                () -> assertEquals("Updated notes", response.getBody().getNotes())
        );
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL;
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(url, Booking[].class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().length > 0)
        );
        System.out.println("All Bookings:");
        for (Booking b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    void e_getByClient() {
        String url = BASE_URL + "/client/" + client.getUserId();
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(url, Booking[].class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().length > 0)
        );
        System.out.println("Client Bookings:");
        for (Booking b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    void f_getByServiceProvider() {
        String url = BASE_URL + "/provider/" + serviceProvider.getUserId();
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(url, Booking[].class);
        assertAll(
                () -> assertNotNull(response.getBody()),
                () -> assertTrue(response.getBody().length > 0)
        );
        System.out.println("Provider Bookings:");
        for (Booking b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    void g_delete() {
        String url = BASE_URL + "/" + booking.getBookingId();
        restTemplate.delete(url);

        ResponseEntity<Booking> response = restTemplate.getForEntity(
                BASE_URL + "/" + booking.getBookingId(),
                Booking.class
        );
        assertNull(response.getBody());
        System.out.println("Deleted: true");
    }
}