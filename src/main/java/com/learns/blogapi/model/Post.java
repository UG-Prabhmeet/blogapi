package com.learns.blogapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.lang.annotation.Native;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Title is required")
    private String title ;

    @NotBlank(message = "Content is required")
    private String content ;

    // many posts → one user (owner of relationship)
    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key in posts table
    private User user;

    // one post → many comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  
    @JsonManagedReference
    private List<Comment> comments ;
}
