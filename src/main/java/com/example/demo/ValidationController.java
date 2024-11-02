package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
public class ValidationController {

    @PostMapping(value = "/validate",
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.TEXT_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity validate(@Valid @RequestBody ValidateRequest validateRequest, @RequestHeader("Content-type") String contentType) {

        System.out.println("Payload Content Type is:"+ contentType );

        MediaType responseMediaType = calculateResponseType();

        System.out.println("Response Content Type is:"+ responseMediaType);


        if (responseMediaType == MediaType.TEXT_XML) {
            XmlMapper xmlMapper = new XmlMapper();
            String response = null;
            try {
                response = xmlMapper.writeValueAsString(validateRequest);
            } catch (JsonProcessingException exception) {
                System.out.println(exception);
            }
            return ResponseEntity.ok().contentType(responseMediaType).body(response);
        }

        if (responseMediaType == MediaType.APPLICATION_JSON) {
            return ResponseEntity.ok().contentType(responseMediaType).body(validateRequest);
        }

        return ResponseEntity.ok().contentType(responseMediaType).body("Test");
    }

    private static final MediaType calculateResponseType() {
        Restconfig config = DemoApplication.getConfig().getRest();

        MediaType mediaType = MediaType.TEXT_PLAIN;

        switch (config.getOutput()) {
            case "J":
                mediaType = MediaType.APPLICATION_JSON;
                break;
            case "X":
                mediaType = MediaType.TEXT_XML;
                break;
            case "R":
            default:
                mediaType = MediaType.TEXT_PLAIN;
        }

        return mediaType;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
