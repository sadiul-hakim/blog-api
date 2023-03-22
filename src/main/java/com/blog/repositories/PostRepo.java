package com.blog.repositories;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> getAllByUser(User user);
    List<Post> getAllByCategory(Category category);
    List<Post> getAllByTitleContaining(String title);
    @Query("select p from Post p where p.title like :keyword")
    List<Post> searchByTitle(@Param("keyword") String keyword);
}
