package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.RepositoryTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RepositoryTestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class JdbcNativeImageRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeEach
    void setUp() {
        template.update("delete from images");
        template.update("delete from posts");
    }

    @Test
    void textCrud() {
        Post post = new Post(null, "Title 1", "Some post text 1.", List.of(), 0, 0);
        Long postId = postRepository.save(post);
        Image temp = new Image(postId, new byte[] {1, 2, 3});
        Image result;

        assertNull(repository.findByPostId(postId).orElse(null));

        repository.update(temp);
        result = repository.findByPostId(postId).orElse(null);
        assertEquals(temp, result);

        temp.setBody(new byte[] {4, 5, 6});
        repository.update(temp);
        result = repository.findByPostId(postId).orElse(null);
        assertEquals(temp, result);
    }
}