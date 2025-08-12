/* BookingServiceImpl.java
   Author: Donna-Lee Jordaan (230613152)
   Date: 25 July 2025 / modified 11 August 2025
*/

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Booking;
import za.co.hireahelper.repository.BookingRepository;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private  BookingRepository repository;

    @Autowired
    public BookingServiceImpl(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking create(Booking booking) {
        return this.repository.save(booking);
    }

    @Override
    public Booking read(String bookingId) {
        return this.repository.findById(bookingId).orElse(null);
    }

    @Override
    public Booking update(Booking booking) {
        return this.repository.save(booking);
    }

    @Override
    public boolean delete(String bookingId) {
        if (this.repository.existsById(bookingId)) {
            this.repository.deleteById(bookingId);
            return true;
        }
        return false;
    }

    @Override
    public List<Booking> getAll() {
        return this.repository.findAll();
    }
}
