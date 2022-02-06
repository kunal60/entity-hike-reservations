package com.entity.app.controller;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.repository.BookingRepository;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookingControllerTest {

    @Autowired
    BookingRepository bookingRepository;

    @LocalServerPort
    int port;

    LocalDate now;
    String controllerPath = "/v1/camping";

    @BeforeEach
    void beforeEach() {
        now = LocalDate.now();
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
        bookingRepository.deleteAll();
    }


    @Test
    void getAllTrails() {
        given()
                .when().get(controllerPath.concat("/trails"))
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void given_existing_trail_id_then_status_ok() {
        given()
                .pathParam("trailId", 1)
                .when().get(controllerPath.concat("/trail") + "/{trailId}")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void given_non_existing_trail_uuid_then_status_not_found() {
        given()
                .pathParam("trailId", 5)
                .when().get(controllerPath.concat("/trail") + "/{trailId}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Nested
    class Add_Booking {

        BookingDto newBookingDto;

        @BeforeEach
        void beforeEach() {
            newBookingDto = null;
        }

        @Test
        void given_booking_age_valid_then_status_created() {
            List<BookingDto> bookingsDto = new ArrayList<>();
            newBookingDto = newBookingDto.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(28).build();
            bookingsDto.add(newBookingDto);

            given()
                    .queryParam("trailId", 1)
                    .queryParam("eventDate", now.plusDays(1).toString())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookingsDto)
                    .when().post(controllerPath + "/booking")
                    .then().statusCode(HttpStatus.OK.value());
        }

        @Test
        void given_multiple_bookings_then_status_created() {
            List<BookingDto> bookingsDto = new ArrayList<>();
            newBookingDto = newBookingDto.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(28).build();
            BookingDto newBookingDto1 = null;
            newBookingDto1 = newBookingDto1.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(28).build();
            bookingsDto.add(newBookingDto);
            bookingsDto.add(newBookingDto1);
            given()
                    .queryParam("trailId", 1)
                    .queryParam("eventDate", now.plusDays(1).toString())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookingsDto)
                    .when().post(controllerPath + "/booking")
                    .then().statusCode(HttpStatus.OK.value());
        }

        @Test
        void given_booking_age_inValid_then_status_bad_request() {
            List<BookingDto> bookingsDto = new ArrayList<>();
            newBookingDto = newBookingDto.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(1).build();
            bookingsDto.add(newBookingDto);

            given()
                    .queryParam("trailId", 1)
                    .queryParam("eventDate", now.plusDays(1).toString())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookingsDto)
                    .when().post(controllerPath + "/booking")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void given_booking_date_inValid_then_status_bad_request() {
            List<BookingDto> bookingsDto = new ArrayList<>();
            newBookingDto = newBookingDto.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(1).build();
            bookingsDto.add(newBookingDto);

            given()
                    .queryParam("trailId", 1)
                    .queryParam("eventDate", now.minusDays(5).toString())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookingsDto)
                    .when().post(controllerPath + "/booking")
                    .then().statusCode(HttpStatus.BAD_REQUEST.value());
        }


    }

    @Nested
    class Cancel_Booking {


        @Test
        void when_booking_Canceled_Then_Status_Ok() {
            long bookingId = getBookingId();
            given()
                    .pathParam("uuid", bookingId)
                    .when().delete(controllerPath.concat("/booking") + "/{uuid}")
                    .then().statusCode(HttpStatus.OK.value());
        }

    }


    @Nested
    class Get_Booking {
        @Test
        void when_booking_Id_Valid_Then_Status_Ok() {
            long bookingId = getBookingId();
            given()
                    .pathParam("uuid", bookingId)
                    .when().get(controllerPath.concat("/booking") + "/{uuid}")
                    .then().statusCode(HttpStatus.OK.value());
        }

    }

    private long getBookingId() {
        BookingDto newBookingDto = null;
        List<BookingDto> bookingsDto = new ArrayList<>();
        newBookingDto = newBookingDto.builder().firstName("Kunal").lastName("Malhotra").email("kunalmalhotra9322@gmail.com").age(28).build();
        bookingsDto.add(newBookingDto);

        String response = given()
                .queryParam("trailId", 1)
                .queryParam("eventDate", now.plusDays(1).toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bookingsDto)
                .when().post(controllerPath + "/booking").asString();
        int end = (response.indexOf("customerId\"") + 15) > response.length() ? response.length() - 1 : (response.indexOf("customerId\"") + 15);
        String output = response.substring(response.indexOf("customerId\"") + 12, end);
        long bookingId = Long.valueOf(output.replaceAll("[^a-zA-Z0-9]", ""));
        return bookingId;
    }


}


  /*
    @Test
    void getBookingById() {
    }

    @Test
    void cancelBooking() {
    }*/