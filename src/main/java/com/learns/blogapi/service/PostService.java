package com.learns.blogapi.service;

import com.learns.blogapi.model.Post;
import com.learns.blogapi.model.User;
import com.learns.blogapi.repository.PostRepository;
import com.learns.blogapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // pageable comes from the controller — carries page number and size
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public List<Post> getPostsByUserName(String name) {
        // this uses our custom @Query from PostRepository
        return postRepository.findPostsByUserName(name);
    }

    public Post createPost(Long userId, Post post) {
        // find the user first, then attach them to the post
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        post.setUser(user);
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}