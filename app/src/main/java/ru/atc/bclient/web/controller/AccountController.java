package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.service.AccountService;
import ru.atc.bclient.web.dto.Notification;
import ru.atc.bclient.web.dto.NotificationType;
import ru.atc.bclient.web.security.AuthorizedUser;

import java.util.Set;

import static ru.atc.bclient.web.controller.AccountController.PATH;

@Controller
@RequestMapping(PATH)
public class AccountController extends AbstractController {
    static final String PATH = "/account";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String viewAccountDetails(Model model, RedirectAttributes redirectAttributes,
                                     @RequestParam("id") Integer accountId, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Set<Account> userAccounts = authorizedUser.getAccounts();

        Account account = accountService.get(accountId);
        if (account == null || !userAccounts.contains(account)) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, "Ошибка: счет не найден."));
            return REDIRECT + "/";
        }
        model.addAttribute(ATTR_ACCOUNT, account);
        model.addAttribute(ATTR_BALANCE, accountService.getBalance(accountId));
        return "accountView";
    }
}
