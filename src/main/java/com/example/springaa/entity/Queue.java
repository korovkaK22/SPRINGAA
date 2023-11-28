package com.example.springaa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "queues")
@Data
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    @NotNull
    private String name;
    @Column(name="is_open")
    @NotNull
    boolean isOpen;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "id")
    User owner;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "queue_lists",
//            joinColumns = @JoinColumn(name = "queue_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<User> users;

    public boolean getIsOpen() {
        return this.isOpen;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isOpen=" + isOpen +
                ", owner=" + owner.getUsername() +
                "}";
    }
}
