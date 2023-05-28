package com.api.arv;

import com.api.arv.model.dto.response.ResponseMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ArvApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArvApiApplication.class, args);
    }

    @GetMapping
    public ResponseEntity<?> getService() {
        return new ResponseEntity<>(new ResponseMessage("Welcome to store api."), HttpStatus.OK);
    }
}
