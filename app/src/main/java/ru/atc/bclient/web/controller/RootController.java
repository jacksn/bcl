package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.web.dto.Notification;
import ru.atc.bclient.web.dto.NotificationType;
import ru.atc.bclient.web.security.AuthorizedUser;

import static ru.atc.bclient.web.controller.ControllerStringConstants.ATTR_LEGAL_ENTITIES;
import static ru.atc.bclient.web.controller.ControllerStringConstants.ATTR_NOTIFICATION;

@Controller
@RequestMapping("/")
public class RootController {
    @GetMapping
    public String root(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute(ATTR_LEGAL_ENTITIES, authorizedUser.getLegalEntities());
        return "accounts";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute(ATTR_NOTIFICATION,
                new Notification(NotificationType.ERROR, "Ошибка входа. Неправильные имя или пароль."));
        return "login";
    }
}
