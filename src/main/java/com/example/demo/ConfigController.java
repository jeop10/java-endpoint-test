package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfigController {

    @GetMapping("/get-config")
    public ResponseEntity getConfig() {
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
                return ResponseEntity.ok().contentType(mediaType).body(config.toString());
        }


        return ResponseEntity.ok().contentType(mediaType).body(config);
    }
}
