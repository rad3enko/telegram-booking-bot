package ru.booking.service.booking;

import ru.booking.model.Booking;
import ru.booking.model.User;
import ru.booking.service.booking.argument.BookingGetSetArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Created on 22.02.2020.
 *
 * @author Sergey Radchenko
 */
public interface BookingService {

    void createSet(LocalDate date, LocalTime from, LocalTime to, int intervalMin);

    void book(UUID id, User user);

    void cancel(UUID id);

    List<Booking> getList(BookingGetSetArgument argument);

    Booking getExistingByDateAndTime(LocalDate date, LocalTime time);

    Booking getExistingById(UUID id);

    Booking getExistingByUserId(Integer userId);
}
