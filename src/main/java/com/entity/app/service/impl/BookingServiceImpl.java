package com.entity.app.service.impl;

import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import com.entity.app.exception.AgeNotValidException;
import com.entity.app.exception.BookingInvalidDateException;
import com.entity.app.model.Status;
import com.entity.app.model.mapper.BookingMapper;
import com.entity.app.repository.BookingRepository;
import com.entity.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Booking> createBookings(List<BookingDto> bookingsDto, Trail trail, LocalDate eventDate) {
        if (!isValidEventDate(eventDate)) {
            throw new BookingInvalidDateException("Event date should be in this format 2022-02-25 and should be with in the date range within next day and next 30 days");
        }
        validateAge(bookingsDto, trail);
        List<Booking> bookings = bookingsDto.stream().map(b -> BookingMapper.INSTANCE.toBooking(b)).collect(Collectors.toList());
        bookings.forEach(booking ->
        {
            booking.setEventDate(eventDate);
            booking.setBookingDate(LocalDate.now());
            booking.setTrail(trail);
            booking.setStatus(Status.BOOKED.name());
        });

        return bookingRepository.saveAll(bookings);

    }

    @Override
    @Transactional
    public Booking cancelBooking(Long uuid) {
        Booking booking = findBookingById(uuid);
        booking.setStatus(Status.CANCELLED.name());
        booking = bookingRepository.save(booking);
        return booking;
    }

    void validateAge(List<BookingDto> bookingsDto, Trail trail) {
        bookingsDto.forEach(booking ->
        {
            if (booking.getAge() < trail.getMinimumAge() || booking.getAge() > trail.getMaximumAge()) {
                throw new AgeNotValidException("Age of user is not permitted for trail:" + booking.getFirstName().concat(booking.getLastName()));
            }
        });

    }

    public boolean isValidEventDate(LocalDate eventDate) {
        return LocalDate.now().isBefore(eventDate)
                && eventDate.isBefore(LocalDate.now().plusMonths(1).plusDays(1));
    }
}