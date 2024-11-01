package com.example.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

@Validated
public class ValidateRequest {

    @NotBlank
    private String command;

    @NotBlank
    private String key;

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

    public @NotBlank String getKey() {
        return key;
    }

    public void setKey(@NotBlank String key) {
        this.key = key;
    }

    public int getLmk() {
        return lmk;
    }

    public void setLmk(int lmk) {
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
