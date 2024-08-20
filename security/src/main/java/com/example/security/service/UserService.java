package com.example.security.service;


import java.util.List;


import com.example.security.entity.User;


 public interface UserService {

    public List<User> getAllUsers();
    public User getUserById(Long id);
    public String save (User user);
    public String editUser(Long id,User user);
} 
