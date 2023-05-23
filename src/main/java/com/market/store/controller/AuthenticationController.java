package com.market.store.controller;

import com.market.store.config.utils.JwtTokenProvider;
import com.market.store.helper.GlobalCommonService;
import com.market.store.model.document.User;
import com.market.store.model.dto.request.Login;
import com.market.store.model.dto.request.SignUp;
import com.market.store.model.dto.response.JsonToken;
import com.market.store.repository.crud.UserRepository;
import com.market.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Timer;

@SuppressWarnings("All")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final Timer timer = new Timer();
    @Autowired
    private GlobalCommonService globalCommonService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Login login) {
        if (login.getUsername().contains("@") && globalCommonService.validateEmail(login.getUsername()))
            return globalCommonService.getResponseEntityByMessageAndStatus("Invalid email", HttpStatus.BAD_REQUEST);

        Authentication authentication;
        final String token;
        ResponseEntity<?> tokenResponseEntity;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            // Todo: notification for login alert

            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenProvider.generateToken(authentication);
            tokenResponseEntity = new ResponseEntity<>(new JsonToken(token), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            tokenResponseEntity = globalCommonService.getResponseEntityByMessageAndStatus("Invalid username or password.", HttpStatus.NOT_FOUND);
        }

        return tokenResponseEntity;
    }


    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody SignUp user) {
        String responseMessage;
        HttpStatus responseStatus = HttpStatus.NOT_ACCEPTABLE;

        boolean isExistUsername = userRepository.findOneByUsername(user.getPhone()).isPresent() || userRepository.findOneByEmail(user.getEmail()).isPresent();
        if (globalCommonService.validateEmail(user.getEmail())) {
            responseMessage = "Invalid email";
        } else if (isExistUsername) {
            responseMessage = !user.getUsername().contains("@") ? "Registered mobile number" : "Registered email id";
        } else {
            responseMessage = "New user created for " + user.getUsername();
            responseStatus = HttpStatus.CREATED;
            userService.save(user);
        }
        return globalCommonService.getResponseEntityByMessageAndStatus(responseMessage, responseStatus);
    }

    @PutMapping("/user")
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

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<>(userRepository.findAll(), responseStatus);
    }
}
