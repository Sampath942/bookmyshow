package com.bms.bookmyshow.repository;

import com.bms.bookmyshow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MyUserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}
