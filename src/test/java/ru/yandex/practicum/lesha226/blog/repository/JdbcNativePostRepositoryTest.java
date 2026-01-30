package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.RepositoryTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringJUnitConfig(classes = RepositoryTestConfig.class)
class JdbcNativePostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeEach
    void setUp() {
        template.update("delete from posts");
        template.update("delete from comments");
    }

    @Test
    void testFindAll() {
        Post temp1Post = new Post(null, "Title 1", "Some text 1", List.of("tag1"), 0, 0);
        Post temp2Post = new Post(null, "Title 2", "Some text 2", List.of("tag2"), 0, 0);
        List<Post> result;

        result = repository.findAll(0, 5);
        assertEquals(List.of(), result);

        repository.save(temp1Post);
        repository.save(temp2Post);

        result = repository.findAll(0, 5);
        assertEquals(List.of(temp1Post, temp2Post), result);

        result = repository.findAll(0, 1);
        assertEquals(List.of(temp1Post), result);

        result = repository.findAll(1, 5);
        assertEquals(List.of(temp2Post), result);
    }

    @Test
    void testCrud() {
        Post tempPost = new Post(null, "Title", "Some text", List.of("tag1"), 0, 0);
        Long id;
        Post result;

        //create
        id = repository.save(tempPost);
        result = repository.findById(id).orElse(null);
        assertEquals(tempPost, result);

        //update
        tempPost.setId(id);
        tempPost.setTitle(tempPost.getTitle() + "(updated)");
        tempPost.setText(tempPost.getText() + "(updated)");
        tempPost.setTags(List.of("tag2"));
        repository.update(tempPost);
        result = repository.findById(id).orElse(null);
        assertEquals(tempPost, result);

        //delete
        repository.delete(id);
        result = repository.findById(id).orElse(null);
        assertNull(result);
    }

    @Test
    void testSize() {
        Post temp1Post = new Post(null, "Title", "Some text", List.of(), 0, 0);
        Post temp2Post = new Post(null, "Title", "Some text", List.of(), 0, 0);
        int result;

        result = repository.size();
        assertEquals(0, result);

        repository.save(temp1Post);
        repository.save(temp2Post);

        result = repository.size();
        assertEquals(2, result);
    }

    @Test
    void testLike() {
        Post tempPost = new Post(null, "Title", "Some text.", List.of(), 0, 0);
        Post result;
        Long id;

        id = repository.save(tempPost);
        result = repository.findById(id).orElse(null);
        assertEquals(tempPost, result);
        repository.like(id);
        tempPost.setLikesCount(tempPost.getLikesCount() + 1);
        result = repository.findById(id).orElse(null);
        assertEquals(tempPost, result);
    }
}