package com.app.marketstore.repository;


import com.app.marketstore.model.AuthenticationToken;
import com.app.marketstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {

//    ค้นหา token ด้วย User
    AuthenticationToken findTokenByUser(User user);
//    ค้นหา token ด้วย token
    AuthenticationToken findTokenByToken(String token);
}
