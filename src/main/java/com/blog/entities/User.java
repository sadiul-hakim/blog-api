package com.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "user name can not be blank")
    @Size(min=4,max=50,message = "user name must be between 4-50 characters")
    private String name;

    @NotBlank(message = "user email can not be blank")
    @Size(min=4,max=50,message = "user email must be between 4-50 characters")
    private String email;

    @NotBlank(message = "user password can not be blank")
    @Size(min=6,max=150)
    private String password;

    @NotBlank(message = "about can not be blank")
    @Size(min=15,max=250,message = "about must be between 15-250 characters")
    private String about;
}
