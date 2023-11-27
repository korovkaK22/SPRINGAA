package com.example.springaa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Table(name="Queue_list")
public class QueueSlot {

    private User user;
    private Queue queue;
    private Long id;

}
