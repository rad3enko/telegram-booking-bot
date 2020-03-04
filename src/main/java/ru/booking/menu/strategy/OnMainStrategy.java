package ru.booking.menu.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.booking.model.Booking;
import ru.booking.model.MenuState;
import ru.booking.service.booking.BookingService;
import ru.booking.service.booking.argument.BookingGetSetArgument;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.Constants;
import ru.booking.util.KeyboardFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 29.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public class OnMainStrategy implements MenuStrategy {

    private final DateTimeFormatter dateFormatter;
    private final BookingService bookingService;
    private final UserDialogService userDialogService;

    public OnMainStrategy(@Qualifier("dateFormatterOut") DateTimeFormatter dateFormatter,
                          @Autowired BookingService bookingService,
                          @Autowired UserDialogService userDialogService) {
        this.dateFormatter = dateFormatter;
        this.bookingService = bookingService;
        this.userDialogService = userDialogService;
    }

    @Override
    public SendMessage getReply(Message message) {
        SendMessage sendMessage = new SendMessage();
        final String receivedText = message.getText();

        if (receivedText.equals(Constants.BOOK_BUTTON)) {
            sendMessage.setText(Constants.PRESS_BOOK_REPLY_TEXT);

            List<LocalDate> dates = bookingService.getList(BookingGetSetArgument.builder()
                                                                                .dateFrom(LocalDate.now())
                                                                                .dateTo(LocalDate.now().plusDays(7))
                                                                                .isBooked(false)
                                                                                .build())
                                                  .stream()
                                                  .map(Booking::getDate)
                                                  .distinct()
                                                  .collect(Collectors.toCollection(LinkedList::new));

            sendMessage.setReplyMarkup(KeyboardFactory.getKeyboardWithDates(dates, dateFormatter));

            userDialogService.setState(message.getFrom().getId(), MenuState.CHOOSING_DATE);

        } else if (receivedText.equals(Constants.ABOUT_BUTTON)) {
            sendMessage.setText(Constants.PRESS_ABOUT_REPLY_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getSentStartKeyboard());
        }
        return sendMessage;
    }
}
