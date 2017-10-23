package ru.atc.bclient.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(PATH)
    public String error(Model model, HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, true);
        log.error("\nError: " + errorAttributes.get("status") + " " + errorAttributes.get("error") +
                "\nPath: " + errorAttributes.get("path") +
                "\nException: " + errorAttributes.get("exception") +
                "\nMessage: " + errorAttributes.get("message"));
        model.addAllAttributes(errorAttributes);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
