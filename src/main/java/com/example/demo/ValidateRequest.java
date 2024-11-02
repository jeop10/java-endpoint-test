package com.example.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

@Validated
public class ValidateRequest {

    @NotBlank
    private String command;

    @Range(min = 1, max = 1234)
    private int key;

    @Range(min = 1, max = 4)
    private int lmk;

    @NotBlank
    private String encoding;

    @NotBlank
    private String output;

    public @NotBlank String getCommand() {
        return command;
    }

    public void setCommand(@NotBlank String command) {
        this.command = command;
    }

    @Range(min = 1, max = 1234)
    public int getKey() {
        return key;
    }

    public void setKey(@Range(min = 1, max = 1234) int key) {
        this.key = key;
    }

    @Range(min = 1, max = 4)
    public int getLmk() {
        return lmk;
    }

    public void setLmk(@Range(min = 1, max = 4) int lmk) {
        this.lmk = lmk;
    }

    public @NotBlank String getEncoding() {
        return encoding;
    }

    public void setEncoding(@NotBlank String encoding) {
        this.encoding = encoding;
    }

    public @NotBlank String getOutput() {
        return output;
    }

    public void setOutput(@NotBlank String output) {
        this.output = output;
    }
}
