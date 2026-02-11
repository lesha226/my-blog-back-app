package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.exception.ImageNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class ImageServiceTest {
    private final Long postId = 1L;
    private final byte[] body = {1, 2, 3};
    private final Image image = new Image(1L, body);

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void testFindByPostId() throws ImageNotFoundException {
        when(repository.findByPostId(postId)).thenReturn(Optional.of(image));

        byte[] result = service.findByPostId(postId);

        verify(repository).findByPostId(postId);
        assertEquals(body, result);
    }

    @Test
    void testFindByPostIdThrowException() {
        when(repository.findByPostId(image.getPostId())).thenReturn(Optional.empty());

        assertThrows(ImageNotFoundException.class, () -> {
            byte[] result = service.findByPostId(image.getPostId());
        });
    }

    @Test
    void testUpdate() throws ImageNotFoundException {
        when(repository.update(image)).thenReturn(true);

        service.update(postId, body);

        verify(repository).update(image);
    }

    @Test
    void testUpdateThrowException() {
        when(repository.update(image)).thenReturn(false);

        assertThrows(ImageNotFoundException.class, () -> {
            service.update(postId, body);
        });
    }
}