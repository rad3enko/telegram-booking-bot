package ru.booking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.booking.model.User;

/**
 * Created on 21.02.2020.
 *
 * @author Sergey Radchenko
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


}
