package ru.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Created on 08.02.2020.
 *
 * @author Sergey Radchenko
 */
@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {

        System.getProperties().put("proxySet", "true");
        System.getProperties().put("socksProxyHost", "127.0.0.1");
        System.getProperties().put("socksProxyPort", "9150");


            ApiContextInitializer.init();


        SpringApplication.run(Launcher.class, args);
    }
}
