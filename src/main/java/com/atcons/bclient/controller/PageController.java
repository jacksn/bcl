package com.atcons.bclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Alexey on 16.08.2017 17:19.
 */
@Controller
public class PageController {
    @GetMapping("/login-process")
    public String processLogin() {
        System.out.println("process-login");
        return "/login";

    }
}
