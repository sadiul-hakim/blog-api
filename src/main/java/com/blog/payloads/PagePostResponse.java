package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PagePostResponse {
    private List<PostDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private boolean lastPage;

}
