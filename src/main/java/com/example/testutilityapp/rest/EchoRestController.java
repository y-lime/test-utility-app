package com.example.testutilityapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.testutilityapp.form.EchoForm;
import com.example.testutilityapp.service.TranslateCaseService;

import jakarta.validation.Valid;

@RestController
@RequestScope
@RequestMapping(value = "api/echo")
public class EchoRestController {
    @Autowired
    private TranslateCaseService translateCaseService;

    @GetMapping(value = "/upper", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public EchoForm getUpperCase(@RequestParam("input") String input) {
        EchoForm echoForm = new EchoForm();
        echoForm.setText(translateCaseService.execute(input));
        return echoForm;
    }

    @PostMapping(value = "/upper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public EchoForm postUpperCase(@RequestBody @Valid EchoForm form) {
        form.setText(translateCaseService.execute(form.getText()));
        return form;
    }
}
