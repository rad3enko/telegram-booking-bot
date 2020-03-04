package ru.booking.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created on 04.03.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public class Constants {

    public static String TEXT_REPLY_FOR_START;
    public static String BOOK_BUTTON;
    public static String PRESS_BOOK_REPLY_TEXT;
    public static String ABOUT_BUTTON;
    public static String PRESS_ABOUT_REPLY_TEXT;
    public static String CHOOSE_TIME_TEXT;
    public static String APPROVE_BOOKING_TEXT;
    public static String APPROVE_BOOKING_BUTTON;
    public static String BACK_TO_TIME_BUTTON;
    public static String MY_BOOKING_BUTTON;
    public static String CANCEL_BOOKING_BUTTON;
    public static String BOOKING_APPROVED_TEXT;
    public static String BOOKING_CANCELED_TEXT;
    public static String MY_BOOKING_TEXT;
    public static String BACK_BUTTON;

    @Value("${BOT_MENU_START_TEXT}")
    public void setTextReplyForStart(String textReplyForStart) {
        TEXT_REPLY_FOR_START = textReplyForStart;
    }

    @Value("${BOT_MENU_BOOK_BUTTON}")
    public void setBookButton(String bookButton) {
        BOOK_BUTTON = bookButton;
    }

    @Value("${BOT_MENU_BOOK_REPLY}")
    public void setPressBookReplyText(String pressBookReplyText) {
        PRESS_BOOK_REPLY_TEXT = pressBookReplyText;
    }

    @Value("${BOT_MENU_ABOUT_BUTTON}")
    public void setAboutButton(String aboutButton) {
        ABOUT_BUTTON = aboutButton;
    }

    @Value("${BOT_MENU_ABOUT_REPLY}")
    public void setPressAboutReplyText(String pressAboutReplyText) {
        PRESS_ABOUT_REPLY_TEXT = pressAboutReplyText;
    }

    @Value("${BOT_MENU_CHOOSE_TIME_TEXT}")
    public void setChooseTimeText(String chooseTimeText) {
        CHOOSE_TIME_TEXT = chooseTimeText;
    }

    @Value("${BOT_MENU_APPROVE_BOOKING_TEXT}")
    public void setApproveBookingText(String approveBookingText) {
        APPROVE_BOOKING_TEXT = approveBookingText;
    }

    @Value("${BOT_MENU_APPROVE_BOOKING_BUTTON}")
    public void setApproveBookingButton(String approveBookingButton) {
        APPROVE_BOOKING_BUTTON = approveBookingButton;
    }

    @Value("${BOT_MENU_BACK_TO_TIME_BUTTON}")
    public void setBackToTimeButton(String backToTimeButton) {
        BACK_TO_TIME_BUTTON = backToTimeButton;
    }

    @Value("${BOT_MENU_ACTIVE_BOOKING_MY_BOOKING_BUTTON}")
    public void setMyBookingButton(String myBookingButton) {
        MY_BOOKING_BUTTON = myBookingButton;
    }

    @Value("${BOT_MENU_CANCEL_BOOKING_BUTTON}")
    public void setCancelBookingButton(String cancelBookingButton) {
        CANCEL_BOOKING_BUTTON = cancelBookingButton;
    }

    @Value("${BOT_MENU_BOOKING_APPROVED_TEXT}")
    public void setBookingApprovedText(String bookingApprovedText) {
        BOOKING_APPROVED_TEXT = bookingApprovedText;
    }

    @Value("${BOT_MENU_BOOKING_CANCELED_TEXT}")
    public void setBookingCanceledText(String bookingCanceledText) {
        BOOKING_CANCELED_TEXT = bookingCanceledText;
    }

    @Value("${BOT_MENU_MY_BOOKING_TEXT}")
    public void setMyBookingText(String myBookingText) {
        MY_BOOKING_TEXT = myBookingText;
    }

    @Value("${BOT_MENU_BACK_BUTTON}")
    public void setBackButton(String backButton) {
        BACK_BUTTON = backButton;
    }
}
