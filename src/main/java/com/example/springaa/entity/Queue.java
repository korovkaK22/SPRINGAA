package com.example.springaa.entity;

import java.util.List;


public class Queue {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    boolean isOpen;
//    @OneToMany(mappedBy = "queue")
    private List<QueueSlot> slots;
    // Гетери та сетери
}
