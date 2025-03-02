package com.bms.bookmyshow.services;

import com.bms.bookmyshow.model.User;
import com.bms.bookmyshow.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    public MyUserRepository repository;

    @Autowired
    public JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=repository.findByUsername(username);
        if(user.isPresent()){
            var userObj=user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }

    public Optional<User> getUser(String jwt){
        String username=jwtService.extractUsername(jwt);
        Optional<User> user=repository.findByUsername(username);
        return user;
    }

    public String[] getRoles(User user){

        if(user.getRole()==null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }

}
