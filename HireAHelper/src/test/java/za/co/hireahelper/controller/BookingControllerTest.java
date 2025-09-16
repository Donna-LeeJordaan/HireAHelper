/* BookingFactoryTest.java
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
import za.co.hireahelper.service.AreaService;
import za.co.hireahelper.service.ClientService;
import za.co.hireahelper.service.ServiceProviderService;
import za.co.hireahelper.service.ServiceTypeService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceProviderService providerService;

    private Booking booking;
    private Client client;
    private ServiceProvider provider;
    private Area area;
    private ServiceType serviceType;

    private final String BASE_URL = "http://localhost:8080/HireAHelper/booking";

    @BeforeEach
    void setUp() {

        area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();
        areaService.create(area);

        serviceType = new ServiceType.Builder()
                .setTypeId("type02")
                .setTypeName("Gardener")
                .build();
        serviceTypeService.create(serviceType);

        client = new Client.Builder()
                .setUserId("user001")
                .setName("Amina")
                .setEmail("amina@example.com")
                .setArea(area)
                .build();
        clientService.create(client);

        provider = new ServiceProvider.Builder()
                .setUserId("user007")
                .setName("Tauriq")
                .setEmail("tauriq@gmail.com")
                .setArea(area)
                .setServiceType(serviceType)
                .build();
        providerService.create(provider);

        booking = BookingFactory.createBooking(
                "booking001",
                LocalDate.of(2025, 4, 12),
                "09:00-12:00", // Added time slot
                "Scheduled",
                "Customer requested morning service",
                client,
                provider
        );
        assertNotNull(booking, "BookingFactory returned null — check input entities");
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Booking> response = restTemplate.postForEntity(BASE_URL + "/create", booking, Booking.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        booking = response.getBody();
        assertNotNull(booking);
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

        Booking check = restTemplate.getForObject(BASE_URL + "/read/" + booking.getBookingId(), Booking.class);
        assertNull(check, "Booking should be null after delete");

        System.out.println("Deleted Booking ID: " + booking.getBookingId());
    }


}

