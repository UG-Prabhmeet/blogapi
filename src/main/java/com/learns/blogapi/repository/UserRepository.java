package com.learns.blogapi.repository;

import com.learns.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA: Java Persistence API
// format: JpaRepository<Entity, PrimaryKeyType>

// WHY DO WE NEED REPOSITORIES?

// Tables = database layer (PostgreSQL) → store raw data & understand only SQL
// Repositories = Java layer → allow us to interact with DB using Java methods (no SQL)

// Controller → Service → Repository → Database

// 1. You call a repository method
//    userRepository.findAll();

// 2. Spring Data JPA intercepts it

// 3. Hibernate (JPA implementation) generates SQL
//    SELECT * FROM users;

// 4. Database returns rows

// 5. Hibernate converts rows → Java objects (User)

// 6. You get List<User> in your code

public interface UserRepository extends JpaRepository<User, Long> {
    // checking if user already exists by an email
    boolean existsByEmail(String email);
}
