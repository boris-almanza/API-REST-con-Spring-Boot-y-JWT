package com.example.security.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.AuthResponse;
import com.example.security.dto.LoginRequest;
import com.example.security.dto.RegisterRequest;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.exceptions.UserAlreadyExistsExeption;
import com.example.security.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    
    
    public AuthResponse login(LoginRequest request) {

           authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

                    User user = userRepository.findByUsername(request.getUsername());
        return  AuthResponse.builder()
                    .token(jwtService.createToken(user))
                    .build();
    }

   

    public String register( RegisterRequest request){
        
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserAlreadyExistsExeption(" El usuario ya existe");
        } 
      
        
        User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode( request.getPassword()))
        .role(Role.ADMIN)
        .build();

        userRepository.save(user);
        return "usuario creado exitosamente";
     }
 

    }
