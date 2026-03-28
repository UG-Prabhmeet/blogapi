package com.learns.blogapi.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;

    // @Enumerated stores the enum as a string ("USER" or "ADMIN")
    // without this, JPA stores it as a number (0 or 1) which is hard to read
    @Enumerated(EnumType.STRING)
    private Role role;

    // one user → many posts (bidirectional relationship)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    // prevents infinite JSON recursion by ignoring posts when serializing User
    private List<Post> posts ;
}
