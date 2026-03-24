package com.learns.blogapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String title ;
    private String content ;

    // many posts → one user (owner of relationship)
    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key in posts table
    @JsonBackReference
    // prevents infinite recursion (back side of User ↔ Post)

    private User user;

    // one post → many comments
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    // mappedBy = "post" → refers to field in Comment entity

    @JsonManagedReference
    // forward side of Post ↔ Comment (included in JSON)
    private List<Comment> comments ;
}
