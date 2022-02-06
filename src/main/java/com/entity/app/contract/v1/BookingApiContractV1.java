package com.entity.app.contract.v1;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import com.entity.app.entity.Trail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
import java.util.List;
import java.util.UUID;

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
            @ApiResponse(responseCode = "204", description = "Trail for a given Id does not exist")})
    @GetMapping(value = "/trail/{trailId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trail> getTrailById(@PathVariable("trailId") Long trailId);

    @Operation(summary = "Book a selected trail for hiking", responses = {
            @ApiResponse(responseCode = "201", description = "Created: new booking was added"),
            @ApiResponse(responseCode = "400", description = "Bad request: new booking was not added")})
    @PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> bookTrail(@RequestBody @Valid List<BookingDto> bookingsDto, @RequestParam("hikeId") Long hikeId);


    @Operation(summary = "View booking for a given UUID", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found: booking for a given UUID does not exist")})
    @GetMapping(value = "/booking/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> getBookingById(@PathVariable("uuid") Long uuid);


    @Operation(summary = "Cancel existing booking", responses = {
            @ApiResponse(responseCode = "200", description = "Success: booking was cancelled"),
            @ApiResponse(responseCode = "400", description = "Bad request: existing booking was not updated"),
            @ApiResponse(responseCode = "404", description = "Not found: booking for a given UUID does not exist")})
    @DeleteMapping(value = "/booking/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> cancelBooking(@PathVariable("uuid") Long uuid);

}