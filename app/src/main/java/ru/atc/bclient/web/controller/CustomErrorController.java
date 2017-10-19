package ru.atc.bclient.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(PATH)
    public String error(Model model, HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        model.addAllAttributes(this.errorAttributes.getErrorAttributes(requestAttributes, false));
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
