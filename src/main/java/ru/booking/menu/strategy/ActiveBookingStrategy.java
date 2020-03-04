package ru.booking.menu.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.booking.model.Booking;
import ru.booking.model.MenuState;
import ru.booking.service.booking.BookingService;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.Constants;
import ru.booking.util.KeyboardFactory;

import java.time.format.DateTimeFormatter;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public class ActiveBookingStrategy implements MenuStrategy {

    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter timeFormatter;
    private UserDialogService userDialogService;
    private BookingService bookingService;

    public ActiveBookingStrategy(@Qualifier("dateFormatterOut") DateTimeFormatter dateFormatter,
                                 @Qualifier("timeFormatter") DateTimeFormatter timeFormatter,
                                 @Autowired UserDialogService userDialogService,
                                 @Autowired BookingService bookingService) {
        this.dateFormatter = dateFormatter;
        this.timeFormatter = timeFormatter;
        this.userDialogService = userDialogService;
        this.bookingService = bookingService;
    }

    @Override
    public SendMessage getReply(Message message) {
        final String textReceived = message.getText();

        int userId = message.getFrom().getId();

        SendMessage sendMessage = new SendMessage();

        Booking booking = bookingService.getExistingByUserId(userId);

        if (textReceived.equals(Constants.MY_BOOKING_BUTTON)) {
            sendMessage.setText(booking.getDate().format(dateFormatter)
                                       .concat(", ")
                                       .concat(booking.getTime().format(timeFormatter)));
            sendMessage.setReplyMarkup(KeyboardFactory.getActiveBookingKeyboard());
        } else if (textReceived.equals(Constants.CANCEL_BOOKING_BUTTON)) {
            bookingService.cancel(booking.getId());
            userDialogService.setState(userId, MenuState.ON_MAIN);

            sendMessage.setText(Constants.BOOKING_CANCELED_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getSentStartKeyboard());
        } else if (textReceived.equals(Constants.ABOUT_BUTTON)) {
            sendMessage.setText(Constants.PRESS_ABOUT_REPLY_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getActiveBookingKeyboard());
        }

        return sendMessage;
    }
}
