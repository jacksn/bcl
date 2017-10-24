package ru.atc.bclient.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;

import static ru.atc.bclient.web.controller.CommonStringConstants.ATTRIBUTE_END_DATE;
import static ru.atc.bclient.web.controller.CommonStringConstants.ATTRIBUTE_NOTIFICATION;
import static ru.atc.bclient.web.controller.CommonStringConstants.ATTRIBUTE_START_DATE;

@Controller
@RequestMapping("/payment")
public class PaymentOrderController {
    private static final String ATTRIBUTE_PAYMENT_ORDER_MAP = "paymentOrderMap";
    private static final String ATTRIBUTE_PAYMENT_ORDER = "paymentOrder";

    private PaymentOrderService paymentOrderService;

    public PaymentOrderController(PaymentOrderService paymentOrderService) {
        this.paymentOrderService = paymentOrderService;
    }

    @GetMapping
    public String getPaymentOrders(Model model,
                                   @RequestParam(value = ATTRIBUTE_START_DATE, required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate starDate,
                                   @RequestParam(value = ATTRIBUTE_END_DATE, required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        LocalDate currentDate = LocalDate.now();
        if (starDate == null && endDate == null) {
            starDate = currentDate;
            endDate = currentDate;
        } else if (endDate == null) {
            endDate = starDate.isBefore(currentDate) ? currentDate : starDate;
        } else if (starDate == null) {
            starDate = endDate.isAfter(currentDate) ? currentDate : endDate;
        } else if (starDate.isAfter(endDate)) {
            endDate = starDate;
        }
        model.addAttribute(ATTRIBUTE_START_DATE, starDate);
        model.addAttribute(ATTRIBUTE_END_DATE, endDate);
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER_MAP,
                paymentOrderService.getAllBySendersGroupByLegalEntityAndAccount(starDate, endDate, authorizedUser.getLegalEntities()));
        return "paymentOrders";
    }

    @GetMapping("view")
    public String viewPaymentOrder(Model model, RedirectAttributes redirectAttributes,
                                   @RequestParam() int id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        PaymentOrder paymentOrder = paymentOrderService.getBySendersAndId(authorizedUser.getLegalEntities(), id);
        if (paymentOrder == null) {
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR, "Ошибка: платежное поручение не найдено."));
            return "redirect:/payment";
        }
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER, paymentOrder);
        return "paymentOrderView";
    }
}
