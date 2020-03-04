package ru.booking.service.userdialog;

import ru.booking.model.MenuState;
import ru.booking.model.User;
import ru.booking.model.UserDialog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Created on 25.02.2020.
 *
 * @author Sergey Radchenko
 */
public interface UserDialogService {

    UserDialog create(User user);

    UserDialog getByUserId(Integer userId);

    void setState(Integer userId, MenuState menuState);

    void setDate(UUID id, LocalDate date);

    void setTime(UUID id, LocalTime time);
}
