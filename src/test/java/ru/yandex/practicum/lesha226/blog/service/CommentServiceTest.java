package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class CommentServiceTest {

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
        final Comment resultComment = new Comment(1L, 100L, "Some comment.");
        when(repository.findAllByPostId(100L)).thenReturn(List.of(resultComment));

        List<Comment> result = service.findAll(100L);

        assertEquals(List.of(resultComment), result);
    }

    @Test
    void findById() {
        final Comment resultComment = new Comment(1L, 100L, "Some comment.");
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.findById(1L).orElse(null);

        verify(repository).findById(1L);
        assertEquals(resultComment, result);
    }

    @Test
    void save() {
        final Comment tempComment = new Comment(null, 100L, "Some comment.");
        final Comment resultComment = new Comment(1L, 100L, "Some comment.");

        when(repository.save(tempComment)).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.save(tempComment).orElse(null);

        verify(repository).save(tempComment);
        verify(repository).findById(1L);
        assertEquals(resultComment, result);
    }

    @Test
    void update() {
        Comment resultComment = new Comment(1L, 100L, "Some comment.");
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.update(resultComment).orElse(null);

        verify(repository).update(resultComment);
        verify(repository).findById(resultComment.getId());
        assertEquals(resultComment, result);
    }

    @Test
    void delete() {
        service.delete(1L);

        verify(repository).delete(1L);
    }
}