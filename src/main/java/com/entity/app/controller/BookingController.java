package com.entity.app.controller;

import com.entity.app.contract.v1.BookingApiContractV1;
import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import com.entity.app.service.BookingService;
import com.entity.app.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Kunal Malhotra
 */
@RestController
public class BookingController implements BookingApiContractV1 {

    @Autowired
    TrailService trailService;

    @Autowired
    BookingService bookingService;

    @Override
    public ResponseEntity<List<Trail>> getAllTrails() {
        return new ResponseEntity<>(trailService.getTrails(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Trail> getTrailById(Long trailId) {
        return new ResponseEntity<>(trailService.findTrailById(trailId), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Booking>> bookTrail(List<BookingDto> bookingsDto, Long trailId, LocalDate eventDate) {
        Trail trail = trailService.findTrailById(trailId);
        return new ResponseEntity<>(bookingService.createBookings(bookingsDto, trail, eventDate), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Long bookingId) {
        return new ResponseEntity<>(bookingService.findBookingById(bookingId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Booking> cancelBooking(Long bookingId) {
        return new ResponseEntity<>(bookingService.cancelBooking(bookingId), HttpStatus.OK);

    }


}
