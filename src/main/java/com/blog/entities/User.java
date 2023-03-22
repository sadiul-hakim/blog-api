package com.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String email;
    @Column(length = 150)
    private String password;
    @Column(length = 250)
    private String about;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Post> posts=new ArrayList<>();
}
