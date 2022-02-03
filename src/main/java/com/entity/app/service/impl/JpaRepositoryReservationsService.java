package com.entity.app.service.impl;

import com.entity.app.entity.Booking;
import com.entity.app.entity.User;
import com.entity.app.repository.BookingRepository;
import com.entity.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class JpaRepositoryReservationsService implements BookingService {

    @Value("${campsite.reservation.length.maximum}")
    private int maximumLengthOfStay;

    @Value("${campsite.reservation.list.default}")
    private int defaultLength;

    @Value("${campsite.reservation.days-ahead.minimum}")
    private int minimumDaysAhead;

    @Value("${campsite.reservation.days-ahead.maximum}")
    private int maximumDaysAhead;

    @Autowired
    private BookingRepository repository;

    private void areDatesAvailable(final Instant startDate, Integer numberOfDays) {

        Instant today = Instant.now().truncatedTo(ChronoUnit.DAYS);

        if (numberOfDays > maximumLengthOfStay) {
            throw new IllegalArgumentException("Length of stay can't exceed " + maximumLengthOfStay + " days. "
                    + "Default: " + defaultLength);
        }

        if (startDate.isBefore(today.plus(minimumDaysAhead, ChronoUnit.DAYS))) {
            throw new IllegalArgumentException("Reservations can't be made before " + minimumDaysAhead
                    + " days in advance");
        }

        if (startDate.isAfter(today.plus(maximumDaysAhead, ChronoUnit.DAYS))) {
            throw new IllegalArgumentException("Reservations can't be made more than " + maximumDaysAhead
                    + " days in advance");
        }

        List<Booking> reservationsConflictingWithRange = repository
                .findReservationsConflictingWithRange(startDate, startDate.plus(numberOfDays, ChronoUnit.DAYS));

        Assert.state(reservationsConflictingWithRange != null,
                "Illegal state: 'reservationsConflictingWithRange' list is null");

        if (!reservationsConflictingWithRange.isEmpty()) {
            throw new IllegalStateException("An existing reservation conflicts with the selected dates");
        }
    }

    @Override
    public List<Booking> listReservationsWithinTimeRange(final Instant startDate, Integer numberOfDays) {
        if (numberOfDays == null) {
            numberOfDays = defaultLength;
        }
        return repository.findReservationsConflictingWithRange(startDate, startDate.plus(numberOfDays, ChronoUnit.DAYS));
    }


    @Override
    @Transactional
    public Booking createReservation(final User userData, final Instant startDate, final Instant endDate) {

        areDatesAvailable(startDate, (int) ChronoUnit.DAYS.between(startDate, endDate));

        Booking newReservation = new Booking(null, null, startDate, endDate, userData);

        return repository.save(newReservation);

    }

    @Override
    @Transactional
    public Booking updateReservation(final Booking reservation) {
        Assert.notNull(reservation.getId(), "Reservation ID cannot be null for UPDATE operations");
        Assert.notNull(reservation.getVersion(), "Reservation version cannot be null for UPDATE operations");
        return repository.save(reservation);
    }

    @Override
    @Transactional
    public void deleteReservation(final Booking reservation) {
        Assert.notNull(reservation.getId(), "Reservation ID cannot be null for DELETE operations");
        Assert.notNull(reservation.getVersion(), "Reservation version cannot be null for DELETE operations");
        repository.delete(reservation);
    }
}
