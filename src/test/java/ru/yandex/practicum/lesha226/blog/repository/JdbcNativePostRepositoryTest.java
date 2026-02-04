package ru.yandex.practicum.lesha226.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.RepositoryTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringJUnitConfig(classes = RepositoryTestConfig.class)
@TestPropertySource(locations = "classpath:test-application.properties")
class JdbcNativePostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Autowired
    private CommentRepository commentRepository;

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
        String searchTitleString = "";
        List<String> searchTagList = List.of();
        List<Post> result;

        result = repository.findAll(searchTitleString, searchTagList, 0, 5);
        assertEquals(List.of(), result);

        repository.save(temp1Post);
        repository.save(temp2Post);

        result = repository.findAll(searchTitleString, searchTagList, 0, 5);
        assertEquals(List.of(temp1Post, temp2Post), result);

        result = repository.findAll(searchTitleString, searchTagList, 0, 1);
        assertEquals(List.of(temp1Post), result);

        result = repository.findAll(searchTitleString, searchTagList, 1, 5);
        assertEquals(List.of(temp2Post), result);

        result = repository.findAll("tle 2", searchTagList, 0, 5);
        assertEquals(List.of(temp2Post), result);

        result = repository.findAll(searchTitleString, temp2Post.getTags(), 0, 5);
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
        Post temp1Post = new Post(null, "Title 1", "Some text 1", List.of("tag1"), 0, 0);
        Post temp2Post = new Post(null, "Title 2", "Some text 2", List.of("tag2"), 0, 0);
        String searchTitleString = "";
        List<String> searchTagList = List.of();
        int result;

        result = repository.size(searchTitleString, searchTagList);
        assertEquals(0, result);

        repository.save(temp1Post);
        repository.save(temp2Post);

        result = repository.size(searchTitleString, searchTagList);
        assertEquals(2, result);

        result = repository.size("tle", searchTagList);
        assertEquals(2, result);

        result = repository.size("tle 1", searchTagList);
        assertEquals(1, result);

        result = repository.size(searchTitleString, List.of("tag1"));
        assertEquals(1, result);
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

    @Test
    void testCommentsCount() {
        Post post = new Post(null, "Title", "Some text.", List.of(), 0, 0);
        Long id;
        Post result;

        id = repository.save(post);
        result = repository.findById(id).orElse(null);
        assertEquals(post, result);

        commentRepository.save(new Comment(null, id, "Some comment text."));
        post.setCommentsCount(1);
        result = repository.findById(id).orElse(null);
        assertEquals(post, result);
    }
}