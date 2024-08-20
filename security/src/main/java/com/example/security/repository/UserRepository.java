package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.User;

@Repository
public interface UserRepository extends  JpaRepository <User, Long> {

   // User findByEmail(String email);
    Boolean existsByUsername( String username);
    User findByUsername( String name);

}
