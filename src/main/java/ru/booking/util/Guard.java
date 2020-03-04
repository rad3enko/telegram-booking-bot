package ru.booking.util;

import lombok.experimental.UtilityClass;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@UtilityClass
public class Guard {

    public static void checkEntityExists(Object object) {
        if (object == null) throw new RuntimeException("Entity not exists.");
    }

    public static void checkArgumentValid(boolean condition) {
        if (!condition) throw new RuntimeException("Argument is not valid.");
    }
}
