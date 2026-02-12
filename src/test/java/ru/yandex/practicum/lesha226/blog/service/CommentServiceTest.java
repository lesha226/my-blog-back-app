package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.dto.CommentResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class CommentServiceTest {
    private final Long id = 1L;
    private final Long postId = 100L;
    private final String tempText = "Some comment.";
    private final CommentCreateDto newDto = new CommentCreateDto(tempText, postId);
    private final CommentUpdateDto updDto = new CommentUpdateDto(id, tempText, postId);
    private final CommentResponseDto dto = new CommentResponseDto(id, tempText, postId);
    private final Comment newComment = new Comment(null, postId, tempText);
    private final Comment resultComment = new Comment(id, postId, tempText);

    @Autowired
    private CommentRepository repository;

    @Autowired
    private CommentService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void findAll() {
        when(repository.findAllByPostId(postId)).thenReturn(List.of(resultComment));

        List<CommentResponseDto> result = service.findAll(postId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void testFindById() throws CommentNotFoundException {
        when(repository.findById(id)).thenReturn(Optional.of(resultComment));

        CommentResponseDto result = service.findById(id);

        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testFindByIdThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            CommentResponseDto result = service.findById(id);
        });
    }

    @Test
    void testSave() {
        when(repository.save(newComment)).thenReturn(id);
        when(repository.findById(id)).thenReturn(Optional.of(resultComment));

        CommentResponseDto result = service.save(newDto);

        verify(repository).save(newComment);
        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testUpdate() throws CommentNotFoundException {
        when(repository.findById(id)).thenReturn(Optional.of(resultComment)).thenReturn(Optional.of(resultComment));
        when(repository.update(resultComment)).thenReturn(true);

        CommentResponseDto result = service.update(id, updDto);

        verify(repository, times(2)).findById(resultComment.getId());
        verify(repository).update(resultComment);
        assertEquals(dto, result);
    }

    @Test
    void testUpdateThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            CommentResponseDto result = service.update(id, updDto);
        });
    }

    @Test
    void testDelete() throws CommentNotFoundException {
        when(repository.delete(id)).thenReturn(true);
        service.delete(id);

        verify(repository).delete(id);
    }

    @Test
    void testDeleteThrowException() {
        when(repository.delete(id)).thenReturn(false);

        assertThrows(CommentNotFoundException.class, () -> {
            service.delete(id);
        });
    }
}