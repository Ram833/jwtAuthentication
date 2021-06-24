package com.stack.route.jwtAuthentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MyController {

    @RequestMapping("/welcome")
    public String getTest(){
        return "Token is genarate and tested";
    }
}
