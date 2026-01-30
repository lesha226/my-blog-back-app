package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.RepositoryTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Image;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RepositoryTestConfig.class)
class JdbcNativeImageRepositoryTest {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeEach
    void setUp() {
        template.update("delete from images");
    }

    @Test
    void textCrud() {
        Image temp = new Image(1L, new byte[] {1, 2, 3});
        Image result;

        repository.update(temp);
        result = repository.findByPostId(temp.getPostId()).orElse(null);
        assertEquals(temp, result);

        temp.setBody(new byte[] {4, 5, 6});
        repository.update(temp);
        result = repository.findByPostId(temp.getPostId()).orElse(null);
        assertEquals(temp, result);
    }
}