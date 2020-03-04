package ru.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created on 21.02.2020.
 *
 * @author Sergey Radchenko
 */
@Configuration
public class BotConfig {
    @Bean
    DefaultBotOptions botOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }

    @Bean(name = "dateFormatterOut")
    DateTimeFormatter dateFormatterOut() {
        return DateTimeFormatter.ofPattern("d MMMM", new Locale("ru", "RU"));
    }

    @Bean(name = "dateFormatterIn")
    DateTimeFormatter dateFormatterIn() {
        return DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru", "RU"));
    }

    @Bean(name = "timeFormatter")
    DateTimeFormatter timeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm", new Locale("ru", "RU"));
    }
}
