package ru.booking.util;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
public class KeyboardBuilder {

    private List<KeyboardRow> keyboardRowList;

    public KeyboardBuilder() {
        keyboardRowList = new LinkedList<>();
    }

    public KeyboardBuilder addMenuRow(KeyboardButton... buttons) {
        KeyboardRow row = new KeyboardRow();
        row.addAll(Arrays.asList(buttons));
        keyboardRowList.add(row);
        return this;
    }

    public KeyboardBuilder addMenuRow(Collection<KeyboardButton> buttons) {
        KeyboardRow row = new KeyboardRow();
        row.addAll(buttons);
        keyboardRowList.add(row);
        return this;
    }

    public ReplyKeyboardMarkup build() {
        if (!keyboardRowList.isEmpty()) {
            return new ReplyKeyboardMarkup(keyboardRowList);
        } else throw new IllegalStateException("Building empty keyboard is not allowed.");
    }
}
