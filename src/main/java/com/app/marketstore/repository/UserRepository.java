package com.app.marketstore.repository;

import com.app.marketstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    ค้นหา User ด้วย Email
    User findByEmail(String email);
}
