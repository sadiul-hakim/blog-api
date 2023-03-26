package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;

    @NotEmpty(message = "user name can not be empty")
    @Size(min=4,max=50,message = "user name must be between 4-50 characters")
    private String name;

    @NotEmpty(message = "user email can not be empty")
    @Email(message = "must be valid email")
    private String email;

    @NotEmpty(message = "user password can not be empty")
    @Size(min=6,max=150)
    private String password;

    @NotEmpty(message = "about can not be empty")
    @Size(min=15,max=250,message = "about must be between 15-250 characters")
    private String about;
    private Set<RoleDTO> roles=new HashSet<>();
}
