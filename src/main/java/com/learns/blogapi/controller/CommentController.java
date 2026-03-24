package com.learns.blogapi.controller;

import com.learns.blogapi.model.Comment;
import com.learns.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    // POST /api/comments?postId=1
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestParam Long postId,
                                                 @RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(postId, comment));
    }

    // DELETE /api/comments/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
