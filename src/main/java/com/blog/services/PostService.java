package com.blog.services;

import com.blog.payloads.PagePostResponse;
import com.blog.payloads.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO dto,Integer userId,Integer categoryId);
    PostDTO updatePost(PostDTO dto,Integer postId);
    void deletePost(Integer postId);
    List<PostDTO> getAllPost();
    PagePostResponse getAllPostPaginated(Integer pageNumber, Integer pageSize,String sortBy,String direction);
    PostDTO getPost(Integer postId);
    List<PostDTO> getAllByUser(Integer userId);
    List<PostDTO> getAllByCategory(Integer categoryId);
    List<PostDTO> searchPost(String keyword);


}
