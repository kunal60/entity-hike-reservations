package com.entity.app.service.impl;

import com.entity.app.entity.Hike;
import com.entity.app.entity.User;
import com.entity.app.repository.UserRepository;
import com.entity.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllPassengersPaged(int pageNum) {
        return userRepository.findAll(PageRequest.of(pageNum, 5, Sort.by("lastName")));
    }

    @Override
    public List<User> getAllPassengers() {
        return userRepository.findAll();
    }

    @Override
    public User getBookingById(Long passengerId) {
        return userRepository.findById(passengerId).orElse(null);
    }


    @Override
    public List<User> saveAllBookins(List<User> passengers, Hike hike) {
        if (isValidAge(passengers, hike)) {
            return userRepository.saveAll(passengers);
        } else throw new RuntimeException("Cannot saving user bookings due to Invalid Age");

    }

    @Override
    public void deleteBookingById(Long passengerId) {
        userRepository.deleteById(passengerId);
    }

    boolean isValidAge(List<User> passengers, Hike hike) {
        return passengers.stream().allMatch(row -> row.getAge() >= hike.getMinimumAge() && row.getAge() <= hike.getMaximumAge());
    }
}