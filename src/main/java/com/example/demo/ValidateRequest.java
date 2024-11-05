package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

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

    public ValidateResponse isValid () {
        ValidateResponse response = new ValidateResponse();

        System.out.println("Received command is "+ this.command);
        if (!this.command.equals("A0")) {
            response.setValid("false");
            response.setMessage("Command not valid");
            return response;
        }

        if (this.key != 1234) {
            response.setValid("false");
            response.setMessage("Key not valid");
            return response;
        }

        if (this.lmk != 4) {
            response.setValid("false");
            response.setMessage("LMK not valid");
            return response;
        }

        if (!this.encoding.equals("V")) {
            response.setValid("false");
            response.setMessage("Encoding not valid");
            return response;
        }


        List<String> validOutputs = new ArrayList<>();
        validOutputs.add("J");
        validOutputs.add("X");

        if (!validOutputs.contains(this.output)) {
            response.setValid("false");
            response.setMessage("Output not valid");
            return response;
        }

        response.setValid("true");
        return response;
    }

}
