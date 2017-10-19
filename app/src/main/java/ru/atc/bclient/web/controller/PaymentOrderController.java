package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.repository.LegalEntityRepository;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.web.security.AuthorizedUser;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentOrderController {
    private PaymentOrderRepository paymentOrderRepository;
    private LegalEntityRepository legalEntityRepository;

    public PaymentOrderController(PaymentOrderRepository paymentOrderRepository, LegalEntityRepository legalEntityRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.legalEntityRepository = legalEntityRepository;
    }

    @GetMapping
    public String getPaymentOrders(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        List<LegalEntity> legalEntities = legalEntityRepository.getByUserId(authorizedUser.getId());
        model.addAttribute("paymentOrders", paymentOrderRepository.getAllBySenderIn(legalEntities));
        return "paymentOrders";
    }
}
