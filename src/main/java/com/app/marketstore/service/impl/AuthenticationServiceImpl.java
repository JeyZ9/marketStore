package com.app.marketstore.service.impl;

import com.app.marketstore.config.MessageStrings;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.model.AuthenticationToken;
import com.app.marketstore.model.User;
import com.app.marketstore.repository.TokenRepository;
import com.app.marketstore.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void saveConfirmationToken(AuthenticationToken authenticationToken){
        tokenRepository.save(authenticationToken);
    }

    @Override
    public AuthenticationToken getToken(User user){
        return tokenRepository.findTokenByUser(user);
    }

    @Override
    public User getUser(String token){
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);

        if(Objects.nonNull(authenticationToken)){
            if(Objects.nonNull(authenticationToken.getUser())){
                return authenticationToken.getUser();
            }
        }

        return null;
    }

    @Override
    public void authenticate(String token) throws AuthenticationFailException{
        if(!Objects.nonNull(token)){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if(!Objects.nonNull(getUser(token))){
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}
