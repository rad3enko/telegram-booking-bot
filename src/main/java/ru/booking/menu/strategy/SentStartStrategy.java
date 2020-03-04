package ru.booking.menu.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.booking.model.MenuState;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.Constants;
import ru.booking.util.KeyboardFactory;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public class SentStartStrategy implements MenuStrategy {

    private UserDialogService userDialogService;

    public SentStartStrategy(@Autowired UserDialogService userDialogService) {
        this.userDialogService = userDialogService;
    }

    @Override
    public SendMessage getReply(Message message) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText(Constants.TEXT_REPLY_FOR_START);
        sendMessage.setReplyMarkup(KeyboardFactory.getSentStartKeyboard());

        userDialogService.setState(message.getFrom().getId(), MenuState.ON_MAIN);

        return sendMessage;
    }
}
