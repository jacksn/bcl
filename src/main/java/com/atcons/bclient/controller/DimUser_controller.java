package com.atcons.bclient.controller;

import com.atcons.bclient.service.DimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alexey on 16.08.2017 15:09.
 */
@RestController
public class DimUser_controller {
    private final
    DimUserService dimUserService;

    @Autowired
    public DimUser_controller(DimUserService dimUserService) {
        this.dimUserService = dimUserService;
    }
    @RequestMapping(value = "/user/{login}", method = RequestMethod.GET)
    public void getDimUser(@PathVariable String login){
        System.out.println(dimUserService.findByUserLogin(login));

    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public void getDimUsers(){
        System.out.println(dimUserService.findAll());

    }
}
