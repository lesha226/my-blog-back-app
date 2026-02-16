package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ComponentScan("ru.yandex.practicum.lesha226.blog.repository")
class JdbcNativeCommentRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("delete from comments");
    }

    @Test
    void testFindAllByPostId() {
        Post post = new Post(null, "Title 1", "Some post text 1.", List.of(), 0, 0);
        Long postId = postRepository.save(post);
        Comment comment1 = new Comment(null, postId, "Some comment 1.");
        Comment comment2 = new Comment(null, postId, "Some comment 2.");

        repository.save(comment1);
        assertEquals(List.of(comment1), repository.findAllByPostId(postId));

        repository.save(comment2);
        assertEquals(List.of(comment1, comment2), repository.findAllByPostId(postId));

        assertTrue(repository.findAllByPostId(-1L).isEmpty());
    }

    @Test
    void testCrud() {
        Post post = new Post(null, "Title 1", "Some post text 1.", List.of(), 0, 0);
        Long postId = postRepository.save(post);
        Comment comment = new Comment(null, postId, "Some comment.");
        Comment result;

        Long id = repository.save(comment);
        comment.setId(id);
        result = repository.findById(id).orElse(null);
        assertEquals(comment, result);

        comment.setText("Some comment (updated)." );
        repository.update(comment);
        result = repository.findById(id).orElse(null);
        assertEquals(comment, result);

        repository.delete(id);
        result = repository.findById(id).orElse(null);
        assertNull(result);
    }

}