package com.entity.app.service.impl;

import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import com.entity.app.exception.AgeNotValidException;
import com.entity.app.exception.BookingInvalidDateException;
import com.entity.app.exception.BookingNotFoundException;
import com.entity.app.model.Status;
import com.entity.app.model.mapper.BookingMapper;
import com.entity.app.repository.BookingRepository;
import com.entity.app.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional(readOnly = true)
    public Booking findBookingById(Long bookingId) {
        Optional<Booking> bookingData = Optional.ofNullable(bookingRepository.findById(bookingId).orElse(null));
        if (bookingData.isPresent()) {
            return bookingData.get();
        }
        log.info(String.format("Booking not found for bookingId %d", bookingId));
        throw new BookingNotFoundException(String.format("Booking not found for bookingId %d", bookingId));
    }


    @Override
    @Transactional
    public List<Booking> createBookings(List<BookingDto> bookingsDto, Trail trail, LocalDate eventDate) {
        if (!isValidEventDate(eventDate)) {
            log.info("Booking cancelled because event date is wrong");
            throw new BookingInvalidDateException("Booking cancelled because event date should be in this format 2022-02-25 and should be with in the date range within next day and next 30 days");
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
    public Booking cancelBooking(Long bookingId) {
        Booking booking = findBookingById(bookingId);
        booking.setStatus(Status.CANCELLED.name());
        booking = bookingRepository.save(booking);
        return booking;
    }

    void validateAge(List<BookingDto> bookingsDto, Trail trail) {
        bookingsDto.forEach(booking ->
        {
            if (booking.getAge() < trail.getMinimumAge() || booking.getAge() > trail.getMaximumAge()) {
                log.info(String.format("Booking cancelled because of %s age", booking.getFirstName() + " " + booking.getLastName() + "'s"));
                throw new AgeNotValidException(String.format("Booking cancelled because of %s age", booking.getFirstName() + " " + booking.getLastName() + "'s"));
            }
        });

    }

    public boolean isValidEventDate(LocalDate eventDate) {
        return LocalDate.now().isBefore(eventDate)
                && eventDate.isBefore(LocalDate.now().plusMonths(1).plusDays(1));
    }
}