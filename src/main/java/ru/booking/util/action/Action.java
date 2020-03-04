package ru.booking.util.action;


/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
public interface Action<T> {

    T execute(ActionArgument argument);

}
