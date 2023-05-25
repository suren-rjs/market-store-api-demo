package com.market.store.controller;

import com.market.store.config.utils.JwtTokenProvider;
import com.market.store.helper.GlobalCommonService;
import com.market.store.model.dto.request.auth.Login;
import com.market.store.model.dto.request.auth.SignUp;
import com.market.store.model.dto.response.auth.JsonToken;
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
        if (login.getPhone().contains("@") && globalCommonService.validateEmail(login.getPhone()))
            return globalCommonService.getResponseEntityByMessageAndStatus("Invalid email", HttpStatus.BAD_REQUEST);

        Authentication authentication;
        final String token;
        ResponseEntity<?> tokenResponseEntity;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getPhone(), login.getUid()));
            // Todo: notification for login alert

            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenProvider.generateToken(authentication);
            tokenResponseEntity = new ResponseEntity<>(new JsonToken(token), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            tokenResponseEntity = globalCommonService.getResponseEntityByMessageAndStatus("Invalid username or password.", HttpStatus.NOT_FOUND);
        }

        return tokenResponseEntity;
    }


    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody SignUp user) {
        String responseMessage;
        HttpStatus responseStatus = HttpStatus.NOT_ACCEPTABLE;

        boolean isExistUser = userRepository.findOneByPhone(user.getPhone()).isPresent() || userRepository.findOneByEmail(user.getEmail()).isPresent();
        if (globalCommonService.validateEmail(user.getEmail())) {
            responseMessage = "Invalid email";
        } else if (isExistUser) {
            responseMessage = !user.getUsername().contains("@") ? "Registered mobile number" : "Registered email id";
        } else {
            responseMessage = "New user created for " + user.getUsername();
            responseStatus = HttpStatus.CREATED;
            userService.save(user);
        }
        return globalCommonService.getResponseEntityByMessageAndStatus(responseMessage, responseStatus);
    }

//    @PostMapping("/verify-email")
//    public ResponseEntity<?> verifyEmail() {
//        String responseMessage = "Verification Success!";
//        HttpStatus responseStatus = HttpStatus.OK;
//        return globalCommonService.getResponseEntityByMessageAndStatus(responseMessage, responseStatus);
//    }

    @PutMapping("/changeActiveStatus/{id}")
    public ResponseEntity<?> disableUser(@RequestParam("id") String id) {
        String responseMessage = "User account has been disabled!";
        HttpStatus responseStatus = HttpStatus.OK;
        userService.changeActiveStatusById(id);
        return globalCommonService.getResponseEntityByMessageAndStatus(responseMessage, responseStatus);
    }
}
