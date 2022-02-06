package com.entity.app.service;


import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;

import java.util.List;
import java.util.UUID;

public interface BookingService {


    Booking findBookingById(Long uuid);


    List<Booking> createBookings(List<Booking> passengers, Trail trail);

    boolean cancelBooking(Long uuid);
}