package ru.yandex.practicum.lesha226.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    private MockMvc mockMvc;

    private final Long notExistId = -1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // fill temp data
        jdbcTemplate.execute("delete from posts");
        for (int i = 0; i < 11; i++) {
            jdbcTemplate.update("""
                    insert into posts(title, text, tags, likes_count)
                    values (?, ?, array['tag'||?], ?)""", "Title" + i, "Text" + i, i/3, i);
        }
    }

    @Test
    void testGetPosts() throws Exception {
        //first page
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("search"    , "")
                        .param("pageNumber", "1")
                        .param("pageSize"  , "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasPrev").value(false))
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.lastPage").value(3))
                .andExpect(jsonPath("$.posts", hasSize(5)))
                .andExpect(jsonPath("$.posts[0].title").value("Title0"));


        //inner page
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("search"    , "")
                        .param("pageNumber", "2")
                        .param("pageSize"  , "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasPrev").value(true))
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.lastPage").value(3))
                .andExpect(jsonPath("$.posts", hasSize(5)));


        //last page
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("search"    , "")
                        .param("pageNumber", "3")
                        .param("pageSize"  , "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasPrev").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.lastPage").value(3))
                .andExpect(jsonPath("$.posts", hasSize(1)));
    }

    @Test
    void testGetPostsWithSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("search", "#tag1")
                        .param("pageNumber", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts", hasSize(3)));

    }

    @Test
    void testGetPost() throws Exception {
        Post post = new Post(null, "Some title.", "Some text", List.of("tag1", "tag2"), 0, 0);
        Long id = postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.text").value(post.getText()))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags[0]").value("tag1"))
                .andExpect(jsonPath("$.tags[1]").value("tag2"))
                .andExpect(jsonPath("$.likesCount").value(0))
                .andExpect(jsonPath("$.commentsCount").value(0));
    }

    @Test
    void testGetPostReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostPost() throws Exception {
        String json = """
                {"title": "Some post title","text": "Some post text.","tags": ["tag_1", "tag_2"]}
                """;
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Some post title"));
    }

    @Test
    void testPutPost() throws Exception {
        Post post = new Post(null, "Some title.", "Some text.", List.of("tag1", "tag2"), 0, 0);
        Long id = postRepository.save(post);
        String json = """
                {"id": ###,
                "title": "Some title.",
                "text": "Some text.(updated)",
                "tags": ["tag1", "tag2", "tag3"]}
                """.replace("###", id.toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.text").value(post.getText() + "(updated)"))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags[0]").value("tag1"))
                .andExpect(jsonPath("$.tags[1]").value("tag2"))
                .andExpect(jsonPath("$.tags[2]").value("tag3"))
                .andExpect(jsonPath("$.likesCount").value(0))
                .andExpect(jsonPath("$.commentsCount").value(0));
    }

    @Test
    void testPutPostReturnsNotFound() throws Exception {
        String json = """
                {"id": ###,
                "title": "Some title.",
                "text": "Some text.(updated)",
                "tags": ["tag1", "tag2", "tag3"]}
                """.replace("###", notExistId.toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/{id}", notExistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePost() throws Exception{
        Post post = new Post(null, "Some title.", "Some text.", List.of("tag1", "tag2"), 0, 0);
        Long id = postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/{id}", id))
                .andExpect(status().isNoContent());

    }

    @Test
    void testDeletePostReturnsNotFound() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/{id}", notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostLike() throws Exception {
        Post post = new Post(null, "Some title.", "Some text.", List.of("tag1", "tag2"), 0, 0);
        Long id = postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/{id}/likes", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/{id}/likes", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void testPostLikeReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/{id}/likes", notExistId))
                .andExpect(status().isNotFound());
    }
}

