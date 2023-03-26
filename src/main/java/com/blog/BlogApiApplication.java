package com.blog;

import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner{
    private final RoleRepo roleRepo;

    public BlogApiApplication(RoleRepo roleRepo){
        this.roleRepo=roleRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role1=new Role();
        role1.setId(100);
        role1.setName("ROLE_ADMIN");

        Role role2=new Role();
        role2.setId(101);
        role2.setName("ROLE_NORMAL");

        List<Role> roles=new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        List<Role> savedRoles = roleRepo.saveAll(roles);
    }
}