package com.market.store.controller;

import com.market.store.helper.GlobalCommonService;
import com.market.store.model.document.User;
import com.market.store.repository.crud.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SuppressWarnings("All")
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private GlobalCommonService globalCommonService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        String responseMessage;
        HttpStatus responseStatus = HttpStatus.NOT_ACCEPTABLE;

        Optional<User> existUser = userRepository.findOneByUsername(user.getUsername());
        if (existUser.isPresent()) {
            User existUserDetails = existUser.get();
            user.setId(existUserDetails.getId());
            userRepository.save(user);
            responseStatus = HttpStatus.ACCEPTED;
            responseMessage = "User details updated !";
        } else {
            responseMessage = "Invalid userid " + user.getUsername();
        }
        return globalCommonService.getResponseEntityByMessageAndStatus(responseMessage, responseStatus);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@RequestParam String id) {
        String message = "Invalid user id";
        HttpStatus status = HttpStatus.OK;
        ResponseEntity<?> response;
        try {
            Optional<User> user = userRepository.findOneById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(user, status);
            } else {
                status = HttpStatus.NOT_FOUND;
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            return globalCommonService.getResponseEntityByMessageAndStatus(message, status);
        }
    }
}
