package com.learns.blogapi.controller;

import com.learns.blogapi.model.Post;
import com.learns.blogapi.service.PostService;
import org.hibernate.engine.spi.Resolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService ;

    public PostController (PostService postService) {
        this.postService = postService ;
    }

    // Spring Data automatically binds query parameters page, size, and sort to the Pageable object

    // Pageable can handle all of these of following requests:
    // /api/posts → default first page, 5 posts per page (because of @PageableDefault(size = 5))
    // /api/posts?page=2&size=10 → third page, 10 posts per page
    // /api/posts?sort=title,asc&sort=createdAt,desc → sorted by title ascending, then by createdAt descending

    @GetMapping
    public Page<Post>getAllPosts(@PageableDefault(size=5)Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    // GET /api/posts/{id} → fetch post by ID
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    // GET /api/posts/user/{userId} → fetch all posts by a specific user
    public List<Post> getPostsByUser(@PathVariable Long userId) {
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/search")
    // GET /api/posts/search?name=xyz → fetch posts by username
    public List<Post> getPostsByUserName(@RequestParam String name) {
        return postService.getPostsByUserName(name);
    }

    @PostMapping
    // POST /api/posts?userId=1 → create a post for a user
    public ResponseEntity<Post> createPost(@RequestParam Long userId , @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId ,post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build() ;
    }
}
