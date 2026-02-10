package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class CommentServiceTest {

    private final Comment tempComment = new Comment(null, 100L, "Some comment.");
    private final Comment resultComment = new Comment(1L, 100L, "Some comment.");

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
        when(repository.findAllByPostId(100L)).thenReturn(List.of(resultComment));

        List<Comment> result = service.findAll(100L);

        assertEquals(List.of(resultComment), result);
    }

    @Test
    void testFindById() throws CommentNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.findById(1L);

        verify(repository).findById(1L);
        assertEquals(resultComment, result);
    }

    @Test
    void testFindByIdThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            Comment result = service.findById(1L);
        });
    }

    @Test
    void testSave() {
        when(repository.save(tempComment)).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.save(tempComment);

        verify(repository).save(tempComment);
        verify(repository).findById(1L);
        assertEquals(resultComment, result);
    }

    @Test
    void testUpdate() throws CommentNotFoundException {
        when(repository.update(resultComment)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(resultComment));

        Comment result = service.update(resultComment);

        verify(repository).update(resultComment);
        verify(repository).findById(resultComment.getId());
        assertEquals(resultComment, result);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void testUpdateThrowException(boolean isUpdated) {
        when(repository.update(resultComment)).thenReturn(isUpdated);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            Comment result = service.update(resultComment);
        });
    }

    @Test
    void testDelete() throws CommentNotFoundException {
        when(repository.delete(1L)).thenReturn(true);
        service.delete(1L);

        verify(repository).delete(1L);
    }

    @Test
    void testDeleteThrowException() {
        when(repository.delete(1L)).thenReturn(false);

        assertThrows(CommentNotFoundException.class, () -> {
            service.delete(1L);
        });
    }
}