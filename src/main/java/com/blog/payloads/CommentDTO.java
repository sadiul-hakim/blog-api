package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private int id;
    @NotEmpty(message = "comment content can not be empty")
    @Size(max=120,message = "comment must be under 120 characters")
    private String content;
    private UserDTO user;
    private Date creationDate;
}
