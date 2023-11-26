package com.example.springaa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Queue {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    boolean isOpen;
    User owner;
//    @OneToMany(mappedBy = "queue")
    private List<QueueSlot> slots;
    // Гетери та сетери
}
