package com.entity.app.repository;

import com.entity.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kunal Malhotra
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}