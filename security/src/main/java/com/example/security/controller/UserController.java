package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import com.example.security.entity.User;
import com.example.security.service.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/v1")
@Validated
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public  String privado( @Valid @RequestBody  User user ){
        return userServiceImpl.save(user);
    }

    

    @PutMapping("/user/{id}")
    public String editUser( @PathVariable Long id, @Valid @RequestBody User user) {
        return userServiceImpl.editUser(id, user);
        
    }
    @GetMapping("/user/{userId}")
    public User getUserbyIdUser(@PathVariable Long userId) {
        return userServiceImpl.getUserById(userId);
        
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
}





}
