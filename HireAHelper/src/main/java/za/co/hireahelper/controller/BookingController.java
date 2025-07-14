/* BookingController.java

   Author: D.Jordaan (230613152)

   Date: 13 July 2025 */

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.service.BookingService;
import java.util.List;

@RestController
@RequestMapping("/booking")
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
}





