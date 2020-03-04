package ru.booking.menu.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.booking.model.Booking;
import ru.booking.model.MenuState;
import ru.booking.model.UserDialog;
import ru.booking.service.booking.BookingService;
import ru.booking.service.booking.argument.BookingGetSetArgument;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.Constants;
import ru.booking.util.KeyboardFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 29.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
@Slf4j
public class ChooseDateStrategy implements MenuStrategy {

    private DateTimeFormatter dateFormatterIn;
    private DateTimeFormatter timeFormatter;
    private UserDialogService userDialogService;
    private BookingService bookingService;

    public ChooseDateStrategy(@Qualifier("dateFormatterIn") DateTimeFormatter dateFormatterIn,
                              @Qualifier("timeFormatter") DateTimeFormatter timeFormatter,
                              @Autowired UserDialogService userDialogService,
                              @Autowired BookingService bookingService) {
        this.dateFormatterIn = dateFormatterIn;
        this.timeFormatter = timeFormatter;
        this.userDialogService = userDialogService;
        this.bookingService = bookingService;
    }

    @Override
    public SendMessage getReply(Message message) {
        final String textReceived = message.getText();

        SendMessage sendMessage = new SendMessage();

        int userId = message.getFrom().getId();

        if (textReceived.equals(Constants.BACK_BUTTON)) {
            userDialogService.setState(userId, MenuState.ON_MAIN);

            sendMessage.setText(Constants.TEXT_REPLY_FOR_START);
            sendMessage.setReplyMarkup(KeyboardFactory.getSentStartKeyboard());
            return sendMessage;
        }

        LocalDate localDate;

        try {
            localDate = LocalDate.parse(addYear(textReceived), dateFormatterIn);

            List<LocalTime> times = bookingService.getList(BookingGetSetArgument.builder()
                                                                                .dateFrom(localDate)
                                                                                .dateTo(localDate)
                                                                                .isBooked(false)
                                                                                .build())
                                                  .stream()
                                                  .map(Booking::getTime)
                                                  .collect(Collectors.toCollection(LinkedList::new));

            UserDialog userDialog = userDialogService.getByUserId(userId);

            userDialogService.setDate(userDialog.getId(), localDate);
            userDialogService.setState(userId, MenuState.CHOOSING_TIME);

            sendMessage.setText(Constants.CHOOSE_TIME_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getKeyboardWithTimes(times, timeFormatter));

        } catch (DateTimeParseException ex) {
            log.error("date: " + textReceived);
            ex.printStackTrace();
        }

        return sendMessage;
    }

    private static String addYear(String date) {
        return date.concat(" ")
                   .concat(String.valueOf(LocalDate.now().getYear()));
    }
}
