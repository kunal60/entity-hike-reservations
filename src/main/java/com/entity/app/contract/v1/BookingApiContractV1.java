package com.entity.app.contract.v1;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Kunal Malhotra
 */
@Tag(name = "Booking V1", description = "Booking API Contract V1")
@RequestMapping("/v1/camping")
public interface BookingApiContractV1 {

    @Operation(summary = "View all the trails available for hiking", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "No Trail exist")})
    @GetMapping(value = "/trails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Trail>> getAllTrails();

    @Operation(summary = "View a selected trail", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Trail for a given Id does not exist")})
    @GetMapping(value = "/trail/{trailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trail> getTrailById(@PathVariable("trailId") Long trailId);

    @Operation(summary = "Book a selected trail for hiking. Event date should be in this format 2022-02-08", responses = {
            @ApiResponse(responseCode = "201", description = "Created: new booking was added"),
            @ApiResponse(responseCode = "400", description = "Bad request: new booking was not added")})
    @PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> bookTrail(@RequestBody @Valid List<BookingDto> bookingsDto, @RequestParam("trailId") Long trailId, @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate eventDate);


    @Operation(summary = "View booking for a given booking Id. Show bookings for both cancelled and booked", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found: booking for a given id does not exist")})
    @GetMapping(value = "/booking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> getBookingById(@PathVariable("bookingId") Long bookingId);


    @Operation(summary = "Cancel existing booking. Change Booking status to cancelled", responses = {
            @ApiResponse(responseCode = "200", description = "Success: booking was cancelled"),
            @ApiResponse(responseCode = "404", description = "Not found: booking for a given id does not exist")})
    @DeleteMapping(value = "/booking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> cancelBooking(@PathVariable("bookingId") Long bookingId);

}