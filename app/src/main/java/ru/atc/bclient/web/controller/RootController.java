package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.web.security.AuthorizedUser;
import ru.atc.bclient.web.to.Notification;
import ru.atc.bclient.web.to.NotificationType;

@Controller
@RequestMapping("/")
public class RootController {
    public static final String NOTIFICATION = "notification";

    @GetMapping
    public String root(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute("legalEntities", authorizedUser.getLegalEntities());
        return "userLegalEntities";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute(NOTIFICATION,
                new Notification(NotificationType.ERROR, "Ошибка входа. Неправильные имя или пароль."));
        return "login";
    }
}
