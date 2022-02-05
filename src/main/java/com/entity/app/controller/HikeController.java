package com.entity.app.controller;

import com.entity.app.entity.Hike;
import com.entity.app.entity.User;
import com.entity.app.service.BookingService;
import com.entity.app.service.HikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/camping")
public class HikeController {

    @Autowired
    HikeService hikeService;

    @Autowired
    BookingService bookingService;

    @GetMapping("/trails")
    public ResponseEntity<List<Hike>> getAllTrails() {
        try {
            List<Hike> hikes = hikeService.getHikes();
            if (hikes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(hikes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trail/{hikeId}")
    public ResponseEntity<Hike> getTrailById(@PathVariable("hikeId") Long hikeId) {
        Optional<Hike> hikeData = hikeService.getHike(hikeId);
        if (hikeData.isPresent()) {
            return new ResponseEntity<>(hikeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/trail/book/new")
    public ResponseEntity<List<User>> bookTrail(@RequestBody List<User> users, @RequestParam("hikeId") Long hikeId) {
        Optional<Hike> flight = hikeService.getHike(hikeId);
        if (flight.isPresent()) {
            users.forEach(passenger -> passenger.setHike(flight.get()));
            bookingService.saveAllBookins(users,flight.get());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/booking/{pnrNumber}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable("pnrNumber") long pnrNumber) {
        try {
            bookingService.deleteBookingById(pnrNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/booking/{pnrNumber}")
    public ResponseEntity<User> getBookingById(@PathVariable("pnrNumber") long pnrNumber) {
        Optional<User> bookingData = Optional.ofNullable(bookingService.getBookingById(pnrNumber));
        if (bookingData.isPresent()) {
            return new ResponseEntity<>(bookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
