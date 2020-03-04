package ru.booking.service.booking.argument;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Created on 22.02.2020.
 *
 * @author Sergey Radchenko
 */
@Getter
@Setter
@Builder
public class BookingGetSetArgument {

    private final Boolean isBooked;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public BookingGetSetArgument(Boolean isBooked, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            throw new IllegalArgumentException("Booking date can't be null.");
        }

        this.isBooked = isBooked;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
