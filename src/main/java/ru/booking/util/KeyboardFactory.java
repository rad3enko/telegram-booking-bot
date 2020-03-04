package ru.booking.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
@UtilityClass
public class KeyboardFactory {

    public static ReplyKeyboardMarkup getSentStartKeyboard() {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        return keyboardBuilder.addMenuRow(new KeyboardButton(Constants.BOOK_BUTTON))
                              .addMenuRow(new KeyboardButton(Constants.ABOUT_BUTTON))
                              .build();
    }

    public static ReplyKeyboardMarkup getKeyboardWithDates(List<LocalDate> dates, DateTimeFormatter dtf) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        keyboardBuilder.addMenuRow(new KeyboardButton(Constants.BACK_BUTTON));

        dates.forEach(date -> keyboardBuilder.addMenuRow(new KeyboardButton(date.format(dtf))));

        return keyboardBuilder.build();
    }

    public static ReplyKeyboardMarkup getKeyboardWithTimes(List<LocalTime> times, DateTimeFormatter dtf) {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        keyboardBuilder.addMenuRow(new KeyboardButton(Constants.BACK_BUTTON));

        times.forEach(date -> keyboardBuilder.addMenuRow(new KeyboardButton(date.format(dtf))));

        return keyboardBuilder.build();
    }

    public static ReplyKeyboardMarkup getApproveBookingKeyboard() {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        return keyboardBuilder.addMenuRow(new KeyboardButton(Constants.APPROVE_BOOKING_BUTTON),
                                          new KeyboardButton(Constants.BACK_TO_TIME_BUTTON))
                              .build();
    }

    public static ReplyKeyboardMarkup getActiveBookingKeyboard() {
        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        return keyboardBuilder.addMenuRow(new KeyboardButton(Constants.MY_BOOKING_BUTTON))
                              .addMenuRow(new KeyboardButton(Constants.CANCEL_BOOKING_BUTTON))
                              .addMenuRow(new KeyboardButton(Constants.ABOUT_BUTTON))
                              .build();
    }
}
