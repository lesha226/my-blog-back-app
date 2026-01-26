package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class PostServiceTest {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void findAll() {
        // TODO : search
    }

    @Test
    void findById() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1", "tag2"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        Post result = service.findById(1L).orElse(null);

        verify(repository).findById(1L);
        assertEquals(post, result);
    }

    @Test
    void size() {
        String search = "Some search"; // TODO: search
        when(repository.size()).thenReturn(3);

        int result = service.size(search);

        verify(repository).size();
        assertEquals(3, result);
    }

    @Test
    void save() {
        final Post tempPost = new Post(null, "Title", "Some text.", List.of("tag1"), 3, 4);
        final Post resultPost = new Post(null, "Title", "Some text.", List.of("tag1"), 3, 4);

        when(repository.save(tempPost)).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(resultPost));

        Post result = service.save(tempPost).orElse(null);

        verify(repository).save(tempPost);
        verify(repository).findById(1L);
        assertEquals(resultPost, result);
    }

    @Test
    void update() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        Post result = service.update(post).orElse(null);

        verify(repository).update(post);
        verify(repository).findById(post.getId());
        assertEquals(post, result);
    }

    @Test
    void delete() {
        service.delete(1L);

        verify(repository).delete(1L);
    }

    @Test
    void like() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        int result = service.like(1L);

        verify(repository).like(1L);
        verify(repository).findById(1L);
        assertEquals(3, result);

    }
}