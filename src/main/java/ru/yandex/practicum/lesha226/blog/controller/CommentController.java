package ru.yandex.practicum.lesha226.blog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@PathVariable("postId") Long postId) {
        List<CommentResponseDto> list = service.findAll(postId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable("postId") Long postId,
                                                         @PathVariable("id") Long id) throws NotFoundException {
        CommentResponseDto responseDto = service.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto>  saveComment(@PathVariable("postId") Long postId,
                                          @Valid @RequestBody CommentCreateDto dto) throws NotCreateException {
        CommentResponseDto responseDto = service.save(dto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto>  updateComment(@PathVariable("postId") Long postId,
                                            @PathVariable("id") Long id,
                                            @Valid @RequestBody CommentUpdateDto dto) throws NotFoundException {
        CommentResponseDto responseDto = service.update(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable(name = "postId") Long postId,
                              @PathVariable(name = "id") Long id) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
