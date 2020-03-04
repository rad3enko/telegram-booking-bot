package ru.booking.service.userdialog;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.booking.model.MenuState;
import ru.booking.model.User;
import ru.booking.model.UserDialog;
import ru.booking.repository.UserDialogRepository;
import ru.booking.util.Guard;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Created on 25.02.2020.
 *
 * @author Sergey Radchenko
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDialogServiceImpl implements UserDialogService {

    private final UserDialogRepository repository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UserDialog create(User user) {
        Guard.checkEntityExists(user);

        UserDialog userDialog = new UserDialog();
        userDialog.setUser(user);
        return repository.save(userDialog);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDialog getByUserId(Integer userId) {
        Guard.checkEntityExists(userId);

        return repository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void setState(Integer userId, MenuState menuState) {
        Guard.checkEntityExists(userId);
        Guard.checkEntityExists(menuState);

        UserDialog userDialog = repository.findByUserId(userId).
                orElseThrow(EntityNotFoundException::new);

        userDialog.setMenuState(menuState);

        repository.save(userDialog);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void setDate(UUID id, LocalDate date) {
        Guard.checkEntityExists(id);
        Guard.checkEntityExists(date);

        UserDialog userDialog = getExisting(id);

        userDialog.setDateChosen(date);
        repository.save(userDialog);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void setTime(UUID id, LocalTime time) {
        Guard.checkEntityExists(id);
        Guard.checkEntityExists(time);

        UserDialog userDialog = getExisting(id);

        userDialog.setTimeChosen(time);
        repository.save(userDialog);
    }

    private UserDialog getExisting(UUID id) {
        Guard.checkEntityExists(id);

        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
