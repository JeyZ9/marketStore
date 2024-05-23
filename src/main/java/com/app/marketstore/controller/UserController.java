package com.app.marketstore.controller;

import com.app.marketstore.dto.users.SigninDTO;
import com.app.marketstore.dto.users.SigninResponseDTO;
import com.app.marketstore.dto.users.SignupDTO;
import com.app.marketstore.dto.users.SignupResponseDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.CustomException;
import com.app.marketstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignupResponseDTO signup(@RequestBody SignupDTO signupDTO) throws CustomException{
        return userService.signup(signupDTO);
    }

    @PostMapping("/signin")
    public SigninResponseDTO signin(@RequestBody SigninDTO signinDTO) throws AuthenticationFailException, CustomException{
        return userService.signin(signinDTO);
    }
}
