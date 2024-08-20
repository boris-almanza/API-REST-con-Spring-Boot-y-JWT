package com.example.security.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    public UserServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }
    

	@Override
	public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll(); 
    }


	@Override
	public User getUserById(Long id) {
        Optional<User> user = userRepository.findById( id);
        if(user.isEmpty()){
            throw new NoSuchElementException("Usuario no encontrado");
        }else{
            return user.get();
        }
    
}

    @Override
    public String save(User user) {
        userRepository.save(user);
        return "usuario creado";
    }


    @Override
    public String editUser(Long id, User userUpdate ) {
        
        User user = getUserById(id);
        user.setUsername(userUpdate.getUsername());
        user.setPassword(userUpdate.getPassword());
        userRepository.save(user);
        return new String("Usuario " +id +" actualizado correctamente");

    }
}