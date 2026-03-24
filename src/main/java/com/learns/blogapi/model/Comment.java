package com.learns.blogapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    // many comments → one post (owner of relationship)
    @ManyToOne
    @JoinColumn(name = "post_id") // foreign key in comments table

    @JsonBackReference
    // back side of Post ↔ Comment (prevents infinite JSON recursion)

    private Post post;
}
