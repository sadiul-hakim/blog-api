package com.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String title;
    @Column(length = 500)
    private String content;
    @Column(length = 40)
    private String imageName;
    private Date creationDate;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
}
