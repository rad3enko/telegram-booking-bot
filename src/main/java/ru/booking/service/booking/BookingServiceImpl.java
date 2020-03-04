package ru.booking.service.booking;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import ru.booking.model.Booking;
import ru.booking.model.QBooking;
import ru.booking.model.User;
import ru.booking.repository.BookingRepository;
import ru.booking.service.booking.argument.BookingGetSetArgument;
import ru.booking.util.Guard;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Created on 22.02.2020.
 *
 * @author Sergey Radchenko
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookingServiceImpl implements BookingService {

    private BookingRepository repository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createSet(LocalDate date, LocalTime from, LocalTime to, int intervalMin) {
        Guard.checkEntityExists(date);
        Guard.checkEntityExists(from);
        Guard.checkEntityExists(to);

        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Booking is not available for past days.");

        if (!to.isAfter(from))
            throw new IllegalArgumentException("Time range error.");

        if (from.plusMinutes(intervalMin).isAfter(to))
            throw new IllegalArgumentException("Interval can't be less than time range.");


        LocalTime currentTime = from;
        List<Booking> bookingList = Lists.newArrayList();

        for (; currentTime.isBefore(to); currentTime = currentTime.plusMinutes(intervalMin)) {
            Booking booking = new Booking();
            booking.setDate(date);
            booking.setTime(currentTime);
            booking.setBooked(false);
            bookingList.add(booking);
        }

        repository.saveAll(bookingList);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void book(UUID id, User user) {
        Guard.checkEntityExists(id);
        Guard.checkEntityExists(user);

        Booking booking = getExistingById(id);

        if (booking.isBooked()) {
            throw new IllegalStateException("Booking is already assigned.");
        }

        booking.setBooked(true);

        booking.setUser(user);

        repository.save(booking);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void cancel(UUID id) {
        Guard.checkEntityExists(id);

        Booking booking = getExistingById(id);

        if (!booking.isBooked()) {
            throw new IllegalStateException("Booking is not assigned at all.");
        }

        booking.setBooked(false);

        booking.setUser(null);

        repository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getList(BookingGetSetArgument argument) {
        Guard.checkEntityExists(argument);

        QBooking qBooking = QBooking.booking;
        BooleanExpression expression = qBooking.id.isNotNull();

        Boolean isBooked = argument.getIsBooked();
        LocalDate dateFrom = argument.getDateFrom();
        LocalDate dateTo = argument.getDateTo();

        if (!ObjectUtils.isEmpty(isBooked)) {
            expression = expression.and(qBooking.booked.eq(isBooked));
        }

        if (!ObjectUtils.isEmpty(dateFrom)) {
            expression = expression.and(qBooking.date.after(dateFrom.minusDays(1)));
        }

        if (!ObjectUtils.isEmpty(dateTo)) {
            expression = expression.and(qBooking.date.before(dateTo.plusDays(1)));
        }

        return Lists.newArrayList(repository.findAll(expression, Sort.by(Sort.Order.asc("date"), Sort.Order.asc("time"))));
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getExistingById(UUID id) {
        return repository.findById(id).orElseThrow((EntityNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getExistingByDateAndTime(LocalDate date, LocalTime time) {
        QBooking qBooking = QBooking.booking;
        return (Booking) repository.findOne(qBooking.date.eq(date)
                                                         .and(qBooking.time.eq(time)))
                                   .orElseGet(() -> {
                                       throw new EntityNotFoundException();
                                   });
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getExistingByUserId(Integer userId) {
        QBooking qBooking = QBooking.booking;
        return (Booking) repository.findOne(qBooking.user.id.eq(userId))
                                   .orElseGet(() -> {throw new EntityNotFoundException();});
    }
}
