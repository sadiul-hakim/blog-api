package com.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private int id;

    @NotEmpty(message = "post title must not be null")
    @Size(min=10,max=100,message = "post title must be between 10-100 characters.")
    private String title;
    @NotEmpty(message = "post content must not be null")
    @Size(min=20,max=450,message = "post title must be between 20-450 characters.")
    private String content;
    private String imageName;
    private Date creationDate;
    private CategoryDTO category;
    private UserDTO user;
    private Set<CommentDTO> comments=new HashSet<>();
}
