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
 * Created on 25.02.2020.
 *
 * @author Sergey Radchenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_dialog")
public class UserDialog {

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

    @Column(name = "date_chosen")
    private LocalDate dateChosen;

    @Column(name = "time_chosen")
    private LocalTime timeChosen;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_state", nullable = false)
    private MenuState menuState = MenuState.SENT_START;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
