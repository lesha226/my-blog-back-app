package ru.yandex.practicum.lesha226.blog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.dto.CommentResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.*;
import ru.yandex.practicum.lesha226.blog.service.CommentService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentResponseDto> getCommentList(@PathVariable("postId") Long postId) {
        return service.findAll(postId);
    }

    @GetMapping("/{id}")
    public CommentResponseDto getComment(@PathVariable("postId") Long postId,
                                         @PathVariable("id") Long id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    public CommentResponseDto saveComment(@PathVariable("postId") Long postId,
                                          @Valid @RequestBody CommentCreateDto dto) throws NotCreateException {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable("postId") Long postId,
                                            @PathVariable("id") Long id,
                                            @Valid @RequestBody CommentUpdateDto dto) throws NotFoundException {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "id") Long id) throws NotFoundException {
        service.delete(id);
    }

}
