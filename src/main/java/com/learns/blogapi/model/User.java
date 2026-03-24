package com.learns.blogapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    private String email ;

    // one user → many posts (bidirectional relationship)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // mappedBy = "user" → refers to 'user' field in Post entity (owns relation)

    @JsonManagedReference
    // prevents infinite JSON recursion (forward side of relation)
    private List<Post> posts ;
}
