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

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Trail flight = trailService.findTrailById(trailId);
        return new ResponseEntity<>(bookingService.createBookings(bookingsDto, flight, eventDate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Booking> getBookingById(Long pnrNumber) {
        Optional<Booking> bookingData = Optional.ofNullable(bookingService.findBookingById(pnrNumber));
        if (bookingData.isPresent()) {
            return new ResponseEntity<>(bookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> cancelBooking(Long uuid) {
        try {
            bookingService.cancelBooking(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}