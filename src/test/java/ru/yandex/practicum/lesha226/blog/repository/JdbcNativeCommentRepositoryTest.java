package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.RepositoryTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Comment;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RepositoryTestConfig.class)
class JdbcNativeCommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("delete from comments");
        jdbcTemplate.update("insert into comments(post_id, text) values (1, 'Some comment #1.')");
        jdbcTemplate.update("insert into comments(post_id, text) values (1, 'Some comment #2.')");
        jdbcTemplate.update("insert into comments(post_id, text) values (2, 'Some comment #3.')");
    }

    @Test
    void testFindAllByPostId() {

        assertEquals(2, repository.findAllByPostId(1L).size());

        assertEquals(1, repository.findAllByPostId(2L).size());

        assertTrue(repository.findAllByPostId(3L).isEmpty());
    }

    @Test
    void testCrud() {
        Comment temp1Comment = new Comment(null, 3L, "Some comment.");
        Comment result;

        Long id = repository.save(temp1Comment);
        temp1Comment.setId(id);
        result = repository.findById(id).orElse(null);
        assertEquals(temp1Comment, result);

        temp1Comment.setText("Some comment (updated)." );
        repository.update(temp1Comment);
        result = repository.findById(id).orElse(null);
        assertEquals(temp1Comment, result);

        repository.delete(id);
        result = repository.findById(id).orElse(null);
        assertNull(result);
    }

}