package ru.booking.util.action;

import ru.booking.util.Guard;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
public abstract class BaseAction<T, A extends ActionArgument> implements Action<T> {

    @Override
    public T execute(ActionArgument argument) {
        Guard.checkEntityExists(argument);
        Guard.checkArgumentValid(argument.isValid());
        return executeImpl((A) argument);
    }

    protected abstract T executeImpl(A argument);
}
