package com.example.testutilityapp.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EchoForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(max = 20)
    private String text;

}