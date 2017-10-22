package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.service.PaymentOrderService;
import ru.atc.bclient.web.security.AuthorizedUser;
import ru.atc.bclient.web.to.Notification;
import ru.atc.bclient.web.to.NotificationType;

@Controller
@RequestMapping("/payment")
public class PaymentOrderController {
    private PaymentOrderService paymentOrderService;

    public PaymentOrderController(PaymentOrderService paymentOrderService) {
        this.paymentOrderService = paymentOrderService;
    }

    @GetMapping
    public String getPaymentOrders(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute("paymentOrderMap",
                paymentOrderService.getAllBySendersGroupByLegalEntityAndAccount(authorizedUser.getLegalEntities()));
        return "paymentOrders";
    }

    @GetMapping("/view")
    public String viewPaymentOrder(Model model,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam() int id,
                                   @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        PaymentOrder paymentOrder = paymentOrderService.getBySendersAndId(authorizedUser.getLegalEntities(), id);
        if (paymentOrder == null) {
            redirectAttributes.addFlashAttribute("notification",
                    new Notification(NotificationType.ERROR, "Ошибка: платежное поручение не найдено."));
            return "redirect:/payment";
        }
        model.addAttribute("paymentOrder", paymentOrder);
        return "paymentOrderView";
    }
}
