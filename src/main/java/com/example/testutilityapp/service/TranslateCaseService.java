package com.example.testutilityapp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class TranslateCaseService {

    public String execute(String input) {
        return StringUtils.upperCase(input);
    }

}
