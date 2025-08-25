/* BookingController.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingController {

    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Booking create(@RequestBody Booking booking) {
        return service.create(booking);
    }

    @GetMapping("/read/{bookingId}")
    public Booking read(@PathVariable String bookingId) {
        return service.read(bookingId);
    }

    @PutMapping("/update")
    public Booking update(@RequestBody Booking booking) {
        return service.update(booking);
    }

    @DeleteMapping("/delete/{bookingId}")
    public boolean delete(@PathVariable String bookingId) {
        return service.delete(bookingId);
    }

    @GetMapping("/all")
    public List<Booking> getAll() {
        return service.getAll();
    }

    // New endpoint for getting bookings by client ID
    @GetMapping("/client/{clientId}")
    public List<Booking> getBookingsByClientId(@PathVariable String clientId) {
        return service.getBookingsByClientId(clientId);
    }

    // New endpoint for getting bookings by service provider ID
    @GetMapping("/service-provider/{serviceProviderId}")
    public List<Booking> getBookingsByServiceProviderId(@PathVariable String serviceProviderId) {
        return service.getBookingsByServiceProviderId(serviceProviderId);
    }
}
