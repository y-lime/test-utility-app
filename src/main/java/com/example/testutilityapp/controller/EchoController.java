package com.example.testutilityapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.RequestScope;

import com.example.testutilityapp.form.EchoForm;
import com.example.testutilityapp.service.TranslateCaseService;

import jakarta.validation.Valid;

@Controller
@RequestScope
@RequestMapping("echo")
public class EchoController {

    @Autowired
    private TranslateCaseService translateCaseService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewInput(Model model) {
        EchoForm form = new EchoForm();
        model.addAttribute(form);
        return "echo/input";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String echo(@Valid EchoForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "echo/input";
        }

        form.setText(translateCaseService.execute(form.getText()));
        return "echo/output";
    }
}