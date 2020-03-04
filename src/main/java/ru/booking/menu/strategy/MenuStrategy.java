package ru.booking.menu.strategy;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@FunctionalInterface
public interface MenuStrategy {

    SendMessage getReply(Message message);

}
