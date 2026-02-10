package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.exception.*;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.service.CommentService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    // TODO : fix front GET http://localhost:8080/api/posts/undefined/comments
    @GetMapping("/undefined/comments")
    public List<Comment> getUndefinedComment() { return Collections.emptyList(); }

    @GetMapping("/{postId}/comments")
    public List<Comment> getCommentList(@PathVariable(name = "postId") Long postId) {
        return service.findAll(postId);
    }

    @GetMapping("/{postId}/comments/{id}")
    public Comment getComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "id") Long id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping("/{postId}/comments")
    public Comment save(@PathVariable(name = "postId") Long postId,
                        @RequestBody Comment comment) throws NotCreateException {
        return service.save(comment);
    }

    @PutMapping("/{postId}/comments/{id}")
    public Comment updateComment(@PathVariable(name = "postId") Long postId,
                                 @PathVariable(name = "id") Long id,
                                 @RequestBody Comment comment) throws NotFoundException {
        return service.update(comment);
    }

    @DeleteMapping("/{postId}/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "id") Long id) throws NotFoundException {
        service.delete(id);
    }

}
