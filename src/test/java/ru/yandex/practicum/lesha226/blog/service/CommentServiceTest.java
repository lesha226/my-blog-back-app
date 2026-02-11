package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.dto.CommentDto;
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
    private final CommentDto newDto = new CommentDto(null, postId, tempText);
    private final CommentDto dto = new CommentDto(id, postId, tempText);
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

        List<CommentDto> result = service.findAll(postId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void testFindById() throws CommentNotFoundException {
        when(repository.findById(id)).thenReturn(Optional.of(resultComment));

        CommentDto result = service.findById(id);

        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testFindByIdThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            CommentDto result = service.findById(id);
        });
    }

    @Test
    void testSave() {
        when(repository.save(newComment)).thenReturn(id);
        when(repository.findById(id)).thenReturn(Optional.of(resultComment));

        CommentDto result = service.save(newDto);

        verify(repository).save(newComment);
        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testUpdate() throws CommentNotFoundException {
        when(repository.update(resultComment)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(resultComment));

        CommentDto result = service.update(dto);

        verify(repository).update(resultComment);
        verify(repository).findById(resultComment.getId());
        assertEquals(dto, result);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void testUpdateThrowException(boolean isUpdated) {
        when(repository.update(resultComment)).thenReturn(isUpdated);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            CommentDto result = service.update(dto);
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