package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private int id;
    @NotEmpty(message = "title must not be empty")
    @Size(min = 2,max = 30,message = "category title must be between 2-30 characters")
    private String title;
    @NotEmpty(message = "description must not be empty")
    @Size(min = 10,max = 100,message = "category title must be between 10-100 characters")
    private String description;
}
