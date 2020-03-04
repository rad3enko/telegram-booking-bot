package ru.booking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.booking.model.UserDialog;

import java.util.Optional;
import java.util.UUID;

/**
 * Created on 25.02.2020.
 *
 * @author Sergey Radchenko
 */
@Repository
public interface UserDialogRepository extends CrudRepository<UserDialog, UUID> {
    Optional<UserDialog> findByUserId(int userId);
}
