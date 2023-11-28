package com.example.springaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueResponse {
   private Integer id;
   private boolean isOpen;
   private String name;
   private UserResponse owner;

    public QueueResponse(Queue queue) {
        this.id = queue.getId();
        this.name = queue.getName();
        this.isOpen = queue.getIsOpen();
        this.owner = new UserResponse(queue.owner);
    }

}
