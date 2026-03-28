package com.learns.blogapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment body is required")
    private String body;

    // Many comments → one post (owner of relationship)
    @ManyToOne
    @JoinColumn(name = "post_id") // foreign key in comments table
    @JsonBackReference
    private Post post;
}
