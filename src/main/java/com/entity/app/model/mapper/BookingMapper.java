package com.entity.app.model.mapper;


import com.entity.app.contract.v1.model.BookingDto;
import com.entity.app.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingDto bookingDto);

}
