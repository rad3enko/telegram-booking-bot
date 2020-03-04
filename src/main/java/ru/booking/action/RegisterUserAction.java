package ru.booking.action;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.booking.action.argument.RegisterUserActionArgument;
import ru.booking.model.User;
import ru.booking.service.user.UserService;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.action.BaseAction;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterUserAction extends BaseAction<User, RegisterUserActionArgument> {

    private UserService userService;
    private UserDialogService userDialogService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    protected User executeImpl(RegisterUserActionArgument argument) {
        User user = userService.create(argument.getId(),
                                       argument.getUsername(),
                                       argument.getName(),
                                       argument.getSurname());

        userDialogService.create(user);

        return user;
    }
}
