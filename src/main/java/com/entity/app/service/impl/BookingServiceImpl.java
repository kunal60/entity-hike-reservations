package com.entity.app.service.impl;

import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import com.entity.app.exception.AgeNotValidException;
import com.entity.app.repository.BookingRepository;
import com.entity.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional(readOnly = true)
    public Booking findBookingById(Long uuid) {
        return bookingRepository.findById(uuid).orElse(null);
    }


    @Override
    @Transactional
    public List<Booking> createBookings(List<Booking> passengers, Trail trail) {
        if (isValidAge(passengers, trail)) {
            return bookingRepository.saveAll(passengers);
        } else throw new AgeNotValidException("Cannot saving user bookings due to Invalid Age");

    }

    @Override
    @Transactional
    public boolean cancelBooking(Long uuid) {
        var booking = findBookingById(uuid);
        booking.setActive(false);
        booking = bookingRepository.save(booking);
        return !booking.getActive();
    }

    boolean isValidAge(List<Booking> passengers, Trail trail) {
        return passengers.stream().allMatch(row -> row.getAge() >= trail.getMinimumAge() && row.getAge() <= trail.getMaximumAge());
    }
}