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
import java.util.stream.Collectors;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
@Component
@Slf4j
public class ChooseTimeStrategy implements MenuStrategy {

    private DateTimeFormatter timeFormatter;
    private DateTimeFormatter dateFormatterOut;
    private UserDialogService userDialogService;
    private BookingService bookingService;

    public ChooseTimeStrategy(@Qualifier("timeFormatter") DateTimeFormatter timeFormatter,
                              @Qualifier("dateFormatterOut") DateTimeFormatter dateFormatterOut,
                              @Autowired UserDialogService userDialogService,
                              @Autowired BookingService bookingService) {
        this.timeFormatter = timeFormatter;
        this.dateFormatterOut = dateFormatterOut;
        this.userDialogService = userDialogService;
        this.bookingService = bookingService;
    }

    @Override
    public SendMessage getReply(Message message) {
        final String textReceived = message.getText();

        SendMessage sendMessage = new SendMessage();

        int userId = message.getFrom().getId();

        if (textReceived.equals(Constants.BACK_BUTTON)) {
            userDialogService.setState(userId, MenuState.CHOOSING_DATE);

            sendMessage.setText(Constants.CHOOSE_TIME_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getKeyboardWithDates(bookingService.getList(BookingGetSetArgument.builder()
                                                                                                                        .dateFrom(LocalDate.now())
                                                                                                                        .dateTo(LocalDate.now().plusDays(7))
                                                                                                                        .isBooked(false)
                                                                                                                        .build())
                                                                                          .stream()
                                                                                          .map(Booking::getDate)
                                                                                          .distinct()
                                                                                          .collect(Collectors.toCollection(LinkedList::new)),
                                                                            dateFormatterOut));
            return sendMessage;
        }

        LocalTime localTime;

        try {
            localTime = LocalTime.parse(textReceived, timeFormatter);

            UserDialog userDialog = userDialogService.getByUserId(userId);

            userDialogService.setTime(userDialog.getId(), localTime);
            userDialogService.setState(userId, MenuState.APPROVING_BOOKING);

            sendMessage.setText(Constants.APPROVE_BOOKING_TEXT);
            sendMessage.setReplyMarkup(KeyboardFactory.getApproveBookingKeyboard());

        } catch (DateTimeParseException ex) {
            log.error("time: " + textReceived);
            ex.printStackTrace();
        }

        return sendMessage;
    }
}
