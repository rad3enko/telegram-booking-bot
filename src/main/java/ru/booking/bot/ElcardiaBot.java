package ru.booking.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.booking.menu.MenuDirector;
import ru.booking.service.booking.BookingService;

import javax.annotation.PostConstruct;

/**
 * Created on 08.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
@Slf4j
public class ElcardiaBot extends TelegramLongPollingBot {

    private TelegramBotsApi telegramBotsApi;
    private MenuDirector menuDirector;
    private BookingService bookingService;

    private static String BOT_NAME;
    private static String BOT_TOKEN;

    public ElcardiaBot(@Autowired TelegramBotsApi telegramBotsApi,
                       @Autowired MenuDirector menuDirector,
                       @Value("${telegram.bot.name}") String botName,
                       @Value("${telegram.bot.token}") String botToken,
                       @Autowired DefaultBotOptions options,
                       @Autowired BookingService bookingService) {
        super(options);
        this.telegramBotsApi = telegramBotsApi;
        this.menuDirector = menuDirector;
        this.bookingService = bookingService;
        BOT_NAME = botName;
        BOT_TOKEN = botToken;
    }

    @PostConstruct
    void registerInApi() {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException ex) {
            ex.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {

        SendMessage sendMessage = menuDirector.getReplyFor(update);

        if (!sendMessage.getText().isEmpty()) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException ex) {
                ex.printStackTrace();
            }
        }
    }


    public String getBotToken() {
        return BOT_TOKEN;
    }

    public String getBotUsername() {
        return BOT_NAME;
    }
}
