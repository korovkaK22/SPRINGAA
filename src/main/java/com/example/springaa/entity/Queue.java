package com.example.springaa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "queues")
@Getter
@Setter
@ToString
@NamedQuery(
        name = "Queue.myCustomNamedQuery",
        query = "SELECT q FROM Queue q WHERE q.id = :id"
)
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @Column(name="is_open")
    @NotNull
    boolean isOpen;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "id")
    User owner;

    public boolean getIsOpen() {
        return this.isOpen;
    }


}
