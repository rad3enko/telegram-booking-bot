package ru.booking.action.argument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.booking.util.action.ActionArgument;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@Builder
@AllArgsConstructor
@Getter
public class RegisterUserActionArgument implements ActionArgument {

    private final Integer id;
    private final String username;
    private final String name;
    private final String surname;

    @Override
    public boolean isValid() {
        return id != null;
    }
}
