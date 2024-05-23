package com.app.marketstore.service.impl;

import com.app.marketstore.config.MessageStrings;
import com.app.marketstore.dto.users.SigninDTO;
import com.app.marketstore.dto.users.SigninResponseDTO;
import com.app.marketstore.dto.users.SignupDTO;
import com.app.marketstore.dto.users.SignupResponseDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.CustomException;
import com.app.marketstore.model.AuthenticationToken;
import com.app.marketstore.model.User;
import com.app.marketstore.repository.UserRepository;
import com.app.marketstore.service.AuthenticationService;
import com.app.marketstore.service.UserService;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private String hashPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    @Override
    public SignupResponseDTO signup(SignupDTO signupDTO) throws CustomException{
        if (Objects.nonNull(userRepository.findByEmail(signupDTO.getEmail()))) {
            throw new CustomException("User already exists!");
        }

        String encryptedPassword = signupDTO.getPassword();
        try {
            encryptedPassword = hashPassword(signupDTO.getPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }

        User user = new User(signupDTO.getFirstName(), signupDTO.getLastName(), signupDTO.getEmail(), encryptedPassword, signupDTO.getPhoneNumber(),signupDTO.getAddress(), signupDTO.getCountry(), signupDTO.getCity(), signupDTO.getPostalCode());
        try {
            userRepository.save(user);

            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);

            return new SignupResponseDTO("success", "User created successfully!");
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public SigninResponseDTO signin(SigninDTO signinDTO) throws AuthenticationFailException, CustomException{
        User user = userRepository.findByEmail(signinDTO.getEmail());

        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("User not present");
        }
        try{
            if (!user.getPassword().equals(hashPassword(signinDTO.getPassword()))){
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Objects.nonNull(token)){
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new SigninResponseDTO("success", token.getToken());
    }
}
