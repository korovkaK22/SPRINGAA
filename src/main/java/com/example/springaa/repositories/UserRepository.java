package com.example.springaa.repositories;

import com.example.springaa.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    public Optional<User> getUserByName(String name){
        //todo =====
        return Optional.of(new User(1L,name,"123"));
    }

}
