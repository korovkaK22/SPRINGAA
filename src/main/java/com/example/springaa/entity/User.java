package com.example.springaa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name= "username")
    @NotNull
    private String username;
    @Column(name="password")
    @NotNull
    private String password;

//    //Він є овнером в цих чергах
//    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
//    private List<Queue> queuesOwner;
//
//    //Він просто є в цих чергах
//    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
//    private List<Queue> queuesGuest;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
