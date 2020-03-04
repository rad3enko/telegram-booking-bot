package ru.booking.service.user;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.booking.model.User;
import ru.booking.repository.UserRepository;
import ru.booking.util.Guard;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * Created on 21.02.2020.
 *
 * @author Sergey Radchenko
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User create(Integer id, String username, String name, String surname) {
        Guard.checkEntityExists(id);

        repository.findById(id).ifPresent((existing) -> {
            throw new EntityExistsException("User id [" +
                                            existing.getId() +
                                            "] already exists.");
        });

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setBlocked(false);

        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> get(Integer id) {
        Guard.checkEntityExists(id);

        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getExisting(Integer id) {
        Guard.checkEntityExists(id);

        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
