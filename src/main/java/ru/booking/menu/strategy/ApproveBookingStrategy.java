package ru.booking.menu.strategy;

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

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public class ApproveBookingStrategy implements MenuStrategy {

    private UserDialogService userDialogService;
    private BookingService bookingService;
    private DateTimeFormatter timeFormatter;

    public ApproveBookingStrategy(@Autowired UserDialogService userDialogService,
                                  @Autowired BookingService bookingService,
                                  @Qualifier("timeFormatter") DateTimeFormatter timeFormatter) {
        this.userDialogService = userDialogService;
        this.bookingService = bookingService;
        this.timeFormatter = timeFormatter;
    }

    @Override
    public SendMessage getReply(Message message) {
        final String textReceived = message.getText();

        int userId = message.getFrom().getId();

        SendMessage sendMessage = new SendMessage();

        UserDialog userDialog = userDialogService.getByUserId(userId);

        if (textReceived.equals(Constants.APPROVE_BOOKING_BUTTON)) {

            Booking booking = bookingService.getExistingByDateAndTime(userDialog.getDateChosen(),
                                                                      userDialog.getTimeChosen());

            bookingService.book(booking.getId(), userDialog.getUser());

            userDialogService.setState(userId, MenuState.ACTIVE_BOOKING);

            sendMessage.setText(Constants.BOOKING_APPROVED_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getActiveBookingKeyboard());

        } else if (textReceived.equals(Constants.BACK_TO_TIME_BUTTON)) {

            userDialogService.setState(userId, MenuState.CHOOSING_TIME);

            sendMessage.setText(Constants.CHOOSE_TIME_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getKeyboardWithTimes(bookingService.getList(BookingGetSetArgument.builder()
                                                                                                                        .dateFrom(userDialog.getDateChosen())
                                                                                                                        .dateTo(userDialog.getDateChosen())
                                                                                                                        .isBooked(false)
                                                                                                                        .build())
                                                                                          .stream()
                                                                                          .map(Booking::getTime)
                                                                                          .collect(Collectors.toCollection(LinkedList::new)), timeFormatter));
        }

        return sendMessage;
    }
}
