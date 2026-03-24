package com.learns.blogapi.repository;

import com.learns.blogapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // get all posts by a specific user
    List<Post> findByUserId(Long userId);

    // pagination support
    // Pageable = page number, size, sorting
    // returns Page object (not just list)
    Page<Post> findAll(Pageable pageable);

    // custom JPQL query (uses entity + field names, not table/column names)
    // p = alias for Post
    // p.user.name → navigates relationship (Post → User → name)
    @Query("SELECT p FROM Post p WHERE p.user.name = :name")
    List<Post> findPostsByUserName(String name);
}