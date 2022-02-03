package com.entity.app.repository;

import java.time.Instant;
import java.util.List;

import com.entity.app.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT r FROM Reservation r WHERE (r.startDate < :endDate AND r.endDate > :startDate) "
            + "OR (r.endDate < :startDate AND r.startDate > :endDate)")
    List<Booking> findReservationsConflictingWithRange(Instant startDate, Instant endDate);

}
