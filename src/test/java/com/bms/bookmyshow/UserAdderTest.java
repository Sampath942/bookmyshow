package com.bms.bookmyshow;

import com.bms.bookmyshow.model.User;
import com.bms.bookmyshow.repository.MyUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class UserAdderTest {
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void addUsers() {

        for(int i = 0; i < 10; i++)
        {
            User user = new User();
            user.setUsername("user_"+i);
            user.setPassword(passwordEncoder.encode("1234"));
            user.setEmail("user_" + i + "@gmail.com");
            user.setPhn("1232456789"+i);
            if(i < 5)
                user.setRole("USER");
            else
                user.setRole("USER,ADMIN");
            myUserRepository.save(user);
        }
    }
}
