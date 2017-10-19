package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.web.security.AuthorizedUser;

import java.util.Set;

@Controller
@RequestMapping("/payment")
public class PaymentOrderController {
    private PaymentOrderRepository paymentOrderRepository;

    public PaymentOrderController(PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @GetMapping
    public String getPaymentOrders(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Set<LegalEntity> legalEntities = authorizedUser.getLegalEntities();
        model.addAttribute("paymentOrders", paymentOrderRepository.getAllBySenderIn(legalEntities));
        return "paymentOrders";
    }
}
