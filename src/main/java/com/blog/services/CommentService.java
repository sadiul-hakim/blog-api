package com.blog.services;

import com.blog.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO dto,Integer postId,Integer userId);
    void deleteComment(Integer commentId);
}
