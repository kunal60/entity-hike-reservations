package com.entity.app.service;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingService {


    Booking findBookingById(Long uuid);


    List<Booking> createBookings(List<BookingDto> bookingsDto, Trail trail, LocalDate eventDate);

    boolean cancelBooking(Long uuid);
}