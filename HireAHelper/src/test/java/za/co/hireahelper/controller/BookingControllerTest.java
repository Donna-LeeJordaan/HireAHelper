/* BookingControllerTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.factory.BookingFactory;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookingControllerTest {  // <-- changed here

    private static Booking booking;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/booking";

    // Minimal placeholders for dependent entities
    private static Client client;
    private static ServiceProvider serviceProvider;

    @BeforeAll
    static void setUp() {
        // Create minimal stub Client and ServiceProvider objects
        client = new Client.Builder()
                .setUserId("client001")
                .build();

        serviceProvider = new ServiceProvider.Builder()
                .setUserId("sp001")
                .build();

        booking = BookingFactory.createBooking(
                "booking001",
                new Date(),           // serviceDate = now
                "Scheduled",
                "Initial booking notes",
                client,
                serviceProvider,
                new ArrayList<>()     // empty reviews list
        );

        assertNotNull(booking); // sanity check
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Booking> response = restTemplate.postForEntity(url, booking, Booking.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + booking.getBookingId();
        ResponseEntity<Booking> response = restTemplate.getForEntity(url, Booking.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        String url = BASE_URL + "/update";

        Booking updated = new Booking.Builder()
                .copy(booking)
                .setStatus("Completed")
                .setNotes("Booking completed successfully")
                .build();

        HttpEntity<Booking> requestEntity = new HttpEntity<>(updated);
        ResponseEntity<Booking> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Booking.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(url, Booking[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("All Bookings:");
        for (Booking b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + booking.getBookingId();
        restTemplate.delete(url);
        System.out.println("Deleted Booking with ID: " + booking.getBookingId());
    }
}
