package com.entity.app.service;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {


    Booking findBookingById(Long bookingId);


    List<Booking> createBookings(List<BookingDto> bookingsDto, Trail trail, LocalDate eventDate);

    Booking cancelBooking(Long bookingId);
}