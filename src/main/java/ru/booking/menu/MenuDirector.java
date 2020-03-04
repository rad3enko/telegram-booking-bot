package ru.booking.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.booking.action.argument.RegisterUserActionArgument;
import ru.booking.menu.strategy.MenuStrategy;
import ru.booking.model.User;
import ru.booking.service.user.UserService;
import ru.booking.service.userdialog.UserDialogService;
import ru.booking.util.action.Action;

/**
 * Created on 28.02.2020.
 *
 * @author Sergey Radchenko
 */
@Component
public final class MenuDirector {

    private final UserService userService;
    private final UserDialogService userDialogService;
    private final Action<User> registerUserAction;
    private final MenuStrategy sentStartStrategy;
    private final MenuStrategy onMainStrategy;
    private final MenuStrategy chooseDateStrategy;
    private final MenuStrategy chooseTimeStrategy;
    private final MenuStrategy approveBookingStrategy;
    private final MenuStrategy activeBookingStrategy;

    private MenuDirector(@Autowired UserService userService,
                         @Autowired UserDialogService userDialogService,
                         @Qualifier("registerUserAction") Action<User> registerUserAction,
                         @Qualifier("sentStartStrategy") MenuStrategy sentStartStrategy,
                         @Qualifier("onMainStrategy") MenuStrategy onMainStrategy,
                         @Qualifier("chooseDateStrategy") MenuStrategy chooseDateStrategy,
                         @Qualifier("chooseTimeStrategy") MenuStrategy chooseTimeStrategy,
                         @Qualifier("approveBookingStrategy") MenuStrategy approveBookingStrategy,
                         @Qualifier("activeBookingStrategy") MenuStrategy activeBookingStrategy) {
        this.userService = userService;
        this.userDialogService = userDialogService;
        this.registerUserAction = registerUserAction;
        this.sentStartStrategy = sentStartStrategy;
        this.onMainStrategy = onMainStrategy;
        this.chooseDateStrategy = chooseDateStrategy;
        this.chooseTimeStrategy = chooseTimeStrategy;
        this.approveBookingStrategy = approveBookingStrategy;
        this.activeBookingStrategy = activeBookingStrategy;
    }

    public SendMessage getReplyFor(Update update) {
        Message message = update.getMessage();

        User user = userService.get(message.getFrom().getId())
                               .orElseGet(() -> registerUserAction.execute(
                                       RegisterUserActionArgument.builder()
                                                                 .id(message.getFrom().getId())
                                                                 .name(message.getFrom().getFirstName())
                                                                 .surname(message.getFrom().getLastName())
                                                                 .username(message.getFrom().getUserName())
                                                                 .build()));

        MenuStrategy menuStrategy;

        switch (userDialogService.getByUserId(user.getId()).getMenuState()) {
            case SENT_START:
                menuStrategy = sentStartStrategy;
                break;
            case ON_MAIN:
                menuStrategy = onMainStrategy;
                break;
            case CHOOSING_DATE:
                menuStrategy = chooseDateStrategy;
                break;
            case CHOOSING_TIME:
                menuStrategy = chooseTimeStrategy;
                break;
            case APPROVING_BOOKING:
                menuStrategy = approveBookingStrategy;
                break;
            case ACTIVE_BOOKING:
                menuStrategy = activeBookingStrategy;
                break;
            default:
                throw new RuntimeException("User menu state is corrupted.");
        }

        SendMessage reply = menuStrategy.getReply(message);
        reply.setChatId(message.getChatId());
        return reply;
    }
}
