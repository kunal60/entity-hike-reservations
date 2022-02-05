package com.entity.app.service;


import com.entity.app.entity.Hike;
import com.entity.app.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    public Page<User> getAllPassengersPaged(int pageNum);

    public abstract List<User> getAllPassengers();

    public abstract User getBookingById(Long passengerId);


    public abstract List<User> saveAllBookins(List<User> passengers, Hike hike);

    public abstract void deleteBookingById(Long pnrId);
}