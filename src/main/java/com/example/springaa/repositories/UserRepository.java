package com.example.springaa.repositories;

import com.example.springaa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> getUserByUsernameIgnoreCase(String name);


}
