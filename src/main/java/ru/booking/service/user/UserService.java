package ru.booking.service.user;

import ru.booking.model.User;

import java.util.Optional;

/**
 * Created on 21.02.2020.
 *
 * @author Sergey Radchenko
 */
public interface UserService {

    User create(Integer id, String username, String name, String surname);

    Optional<User> get(Integer id);

    User getExisting(Integer id);
}
