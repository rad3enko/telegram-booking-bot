package ru.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Created on 20.02.2020.
 *
 * @author Sergey Radchenko
 */
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID",
            unique = true,
            nullable = false,
            updatable = false,
            length = 36)
    private UUID id;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false, updatable = false)
    private LocalTime time;

    @Column(name = "booked", nullable = false)
    private boolean booked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
