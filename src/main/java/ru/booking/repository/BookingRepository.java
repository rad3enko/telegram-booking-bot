package ru.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.booking.model.Booking;

import java.util.UUID;

/**
 * Created on 22.02.2020.
 *
 * @author Sergey Radchenko
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID>, QuerydslPredicateExecutor {

}
