package com.example.testutilityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestScope
public class WelcomeController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}