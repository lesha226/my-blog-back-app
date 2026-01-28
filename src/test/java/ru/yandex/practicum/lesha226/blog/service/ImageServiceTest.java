package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class ImageServiceTest {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ImageService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void testFindAllByPostId() {
        Image temp = new Image(1L, new byte[] {1, 2, 3});
        when(repository.findByPostId(temp.getPostId())).thenReturn(Optional.of(temp));

        Image result = service.findByPostId(temp.getPostId()).orElse(null);

        verify(repository).findByPostId(temp.getPostId());
        assertEquals(temp, result);
    }

    @Test
    void testUpdate() {
        Image temp = new Image(1L, new byte[] {1, 2, 3});

        service.update(temp);

        verify(repository).update(temp);
    }
}