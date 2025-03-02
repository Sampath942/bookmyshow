package com.bms.bookmyshow.controller;

import com.bms.bookmyshow.model.LoginForm;
import com.bms.bookmyshow.model.User;
import com.bms.bookmyshow.repository.MyUserRepository;
import com.bms.bookmyshow.services.JwtService;
import com.bms.bookmyshow.services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class RegistrationController {
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;
    public RegistrationController(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @PostMapping("/register/user")
    public User createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myUserRepository.save(user);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Map<String,Object>> authenticateAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(),loginForm.password()
        ));
        if(authentication.isAuthenticated()){
            String jwt=jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
            Map<String,Object> response=new HashMap<>();
            Optional<User> user=myUserDetailService.getUser(jwt);
            response.put("jwt_token",jwt);
            response.put("User details",user);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
        }else{
            throw new UsernameNotFoundException("invalid credentials");
        }
    }
}
