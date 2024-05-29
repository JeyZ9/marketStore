package com.app.marketstore.controller;

import com.app.marketstore.dto.users.SigninDTO;
import com.app.marketstore.dto.users.SigninResponseDTO;
import com.app.marketstore.dto.users.SignupDTO;
import com.app.marketstore.dto.users.SignupResponseDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.CustomException;
import com.app.marketstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "The user controller")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Sign up", description = "Register a new user")
    @PostMapping("/signup")
    public SignupResponseDTO signup(@RequestBody SignupDTO signupDTO) throws CustomException{
        return userService.signup(signupDTO);
    }

    @Operation(summary = "Sign in", description = "login for user")
    @PostMapping("/signin")
    public SigninResponseDTO signin(@RequestBody SigninDTO signinDTO) throws AuthenticationFailException, CustomException{
        return userService.signin(signinDTO);
    }
}
