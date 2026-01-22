package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.model.Comment;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class CommentController {
    // TODO : delete tempComment
    private final Comment tempComment = new Comment(100L, "Some comment.", 1L);

    // TODO : fix front GET http://localhost:8080/api/posts/undefined/comments
    @GetMapping("/undefined/comments")
    public List<Comment> getUndefinedComment() {
        return Collections.emptyList();
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getCommentList(@PathVariable(name = "postId") Long postId) {
        return List.of(tempComment); // TODO : use service
    }

    @GetMapping("/{postId}/comments/{id}")
    public Comment getComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "id") Long id) {
        return tempComment; // TODO : use service
    }

    @PostMapping("/{postId}/comments")
    public Comment save(@PathVariable(name = "postId") Long postId, @RequestBody Comment comment) {
        return tempComment; // TODO : use service
    }

    @PutMapping("/{postId}/comments/{id}")
    public Comment updateComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "id") Long id,
                                 @RequestBody Comment comment) {
        return tempComment; // TODO : user service
    }

    @DeleteMapping("/{postId}/comments/{id}")
    public void deleteComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "id") Long id) {
        // TODO : use service
    }

}
