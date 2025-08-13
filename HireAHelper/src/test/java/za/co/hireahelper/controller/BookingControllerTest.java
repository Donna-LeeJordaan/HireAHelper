/* BookingControllerTest.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/


package za.co.hireahelper.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.co.hireahelper.domain.*;
import za.co.hireahelper.factory.BookingFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingControllerTest {

    private static Booking booking;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/HireAHelper/booking";

    @BeforeAll
    static void setup() {
        // Create minimal stub objects assuming they already exist in DB
        Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        Client client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(area)
                .build();

        ServiceProvider serviceProvider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(area)
                .build();

        booking = BookingFactory.createBooking(
                "booking001",
                LocalDate.of(2025, 4, 12),
                "Scheduled",
                "Customer requested morning service",
                client,
                serviceProvider,
                new ArrayList<>()
        );
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Booking> response = restTemplate.postForEntity(BASE_URL + "/create", booking, Booking.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingId(), response.getBody().getBookingId());
        booking = response.getBody(); // update booking with persisted version
        System.out.println("Created Booking: " + booking);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Booking> response = restTemplate.getForEntity(BASE_URL + "/read/" + booking.getBookingId(), Booking.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(booking.getBookingId(), response.getBody().getBookingId());
        System.out.println("Read Booking: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Booking updatedBooking = new Booking.Builder()
                .copy(booking)
                .setStatus("Confirmed")
                .setNotes("Booking confirmed")
                .build();

        HttpEntity<Booking> request = new HttpEntity<>(updatedBooking);
        ResponseEntity<Booking> response = restTemplate.exchange(BASE_URL + "/update", HttpMethod.PUT, request, Booking.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Confirmed", response.getBody().getStatus());

        booking = response.getBody();
        System.out.println("Updated Booking: " + booking);
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<Booking[]> response = restTemplate.getForEntity(BASE_URL + "/all", Booking[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Bookings:");
        for (Booking b : response.getBody()) {
            System.out.println(b);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + booking.getBookingId());

        ResponseEntity<Booking> response = restTemplate.getForEntity(BASE_URL + "/read/" + booking.getBookingId(), Booking.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted Booking with ID: " + booking.getBookingId());
    }
}
