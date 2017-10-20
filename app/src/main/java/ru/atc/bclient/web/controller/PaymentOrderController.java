package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.service.PaymentOrderService;
import ru.atc.bclient.web.security.AuthorizedUser;

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
}
