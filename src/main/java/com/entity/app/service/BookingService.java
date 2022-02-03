package com.entity.app.service;

import com.entity.app.entity.Booking;
import com.entity.app.entity.User;

import java.time.Instant;
import java.util.List;

/**
 * A service interface exposing CRUD methods for making reservations to
 * the campsite.
 *
 * @author Kunal Malhotra
 * @see com.entity.app.entity.Booking
 */
public interface BookingService {

    /**
     * Lists reservations 'conflicting' with the given time range. This means that the method will list
     * all reservations conflicting with the specified start date and/or end date, plus all
     * reservations contained within the specified range.
     * <p>
     * If no length is provided for the time range (numberOfDays = null), the default will be used.
     *
     * @param startDate    start date (Instant) of time range for which campsite availability is requested
     * @param numberOfDays length of time range for which campsite availability is requested (in days).
     *                     Defaults to the {@code campsite.reservation.list.default} config parameter.
     * @return reservations 'conflicting' with the given time range.
     */
    List<Booking> listReservationsWithinTimeRange(Instant startDate, Integer numberOfDays);

    /**
     * Creates a reservation for the provided {@link User} between two
     * given dates. The length of the reservation cannot exceed the amount
     * of days specified by the {@code campsite.reservation.length.maximum}
     * configuration property.
     *
     * @param userData  information about the user that will own the reservation
     * @param startDate date (Instant) in which the reservation starts
     * @param endDate   date (Instant) in which the reservation ends
     * @return the reservation between the requested dates for the specified user, containing an
     * automatically generated booking id
     * @throws IllegalArgumentException if:
     *                                  - length of stay exceeds {@code campsite.reservation.length.maximum} or
     *                                  - reservation start date comes before {@code campsite.reservation.days-ahead.minimum} or
     *                                  - reservation start date comes after {@code campsite.reservation.days-ahead.maximum}
     */
    Booking createReservation(User userData, Instant startDate, Instant endDate);

    /**
     * Updates a given {@link Reservation}, uniquely identified by its {@code id}.
     * This operation uses optimistic locking for preventing silent updates.
     *
     * @param reservation contains the information for the reservation that is to be updated,
     *                    uniquely identified by its {@code id}
     * @return the updated reservation information
     * @throws ObjectOptimisticLockingFailureException if a stale copy of the reservation attempts
     *                                                 to be updated
     */
    Booking updateReservation(Booking reservation);

    /**
     * Deletes a given {@link Reservation}, uniquely identified by its {@code id}.
     * This operation uses optimistic locking for preventing silent deletes.
     *
     * @param reservation contains the information for the reservation that is to be deleted,
     *                    uniquely identified by its {@code id}
     * @throws ObjectOptimisticLockingFailureException if a stale copy of the reservation attempts
     *                                                 to be deleted
     */
    void deleteReservation(Booking reservation);

}