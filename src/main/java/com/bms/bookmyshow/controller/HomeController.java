package com.bms.bookmyshow.controller;

import com.bms.bookmyshow.model.LoginForm;
import com.bms.bookmyshow.model.Movie;
import com.bms.bookmyshow.model.User;
import com.bms.bookmyshow.services.JwtService;
import com.bms.bookmyshow.services.MovieService;
import com.bms.bookmyshow.services.MyUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    public JwtService jwtService;

    @Autowired
    public MyUserDetailService myUserDetailService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/home")
    public ResponseEntity<?> handleHome(HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        String jwt_token;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt_token=authHeader.substring(7);

            if(jwtService.isTokenValid(jwt_token)){
                Optional<User> user = myUserDetailService.getUser(jwt_token);
                if(user.isPresent()) {
                    User userObj = user.get();

                    String[] roles = myUserDetailService.getRoles(userObj);
                    boolean isAdmin = false;
                    for (String role : roles) {
                        if (role.compareTo("ADMIN") == 0) {
                            isAdmin = true;
                            break;
                        }
                    }
                    if (!isAdmin)
                        return ResponseEntity.status(HttpStatus.FOUND).
                                header("location", "/user/home").
                                build();
                    return ResponseEntity.status(HttpStatus.FOUND).
                            header("location", "/admin/home").
                            build();
                }
            }
        }
        List<Movie> top10Movies = movieService.getTop10MostRecentMovies();
        return new ResponseEntity<>(top10Movies, HttpStatus.OK);
    }

    @GetMapping("/admin/home")
    public  String handleAdminHome(){
        return "home_admin";
    }

    @GetMapping("/user/home")
    public ResponseEntity<Map<String,Object>> getUserDetails(@RequestHeader("Authorization") String authHeader){
        Map<String,Object> response=new HashMap<>();
        String jwt=authHeader.substring(7);
        Optional<User> user=myUserDetailService.getUser(jwt);
        response.put("User", user);
        response.put("Movies", movieService.loadMovies());
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
//    @GetMapping("/login")
//    public String handleLogin(){
//        return "custom_login";
//    }
    @GetMapping("/logout")
    public String handleLogout(){
        return "logout";
    }


}

