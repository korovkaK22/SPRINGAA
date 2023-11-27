package com.example.springaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    long id;
    String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
