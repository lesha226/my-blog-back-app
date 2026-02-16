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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class CommentControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;
    private Long postId;
    private Long commentId;
    private final Long notExistId = -1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // fill temp data
        jdbcTemplate.execute("delete from posts");
        jdbcTemplate.update("""
                insert into posts(title, text)
                values
                ('Title1', 'Some text 1.'),
                ('Title2', 'Some text 2.')""");
        jdbcTemplate.update("""
                insert into comments(post_id, text)
                select p.id, 'Some comment '||t.v||'.'
                from posts p, unnest(array['1','2','3']) t(v)""");
        postId = jdbcTemplate.queryForObject("""
                select id from posts order by id limit 1""", Long.class);
        commentId = jdbcTemplate.queryForObject("""
                select id
                from comments
                where post_id = ?
                  and text = 'Some comment 1.'
                limit 1""", Long.class, postId);
    }

    @Test
    void testGetComments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].postId").value(postId))
                .andExpect(jsonPath("$[0].text").value("Some comment 1."))
                .andExpect(jsonPath("$[1].text").value("Some comment 2."))
                .andExpect(jsonPath("$[2].text").value("Some comment 3."));

    }

    @Test
    void testGetCommentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments/{id}", postId, commentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.text").value("Some comment 1."));
    }

    @Test
    void testGetCommentByIdReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments/{id}", postId, notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostComment() throws Exception {
        String json = """
                {
                    "postId": ###,
                    "text": "Some comment."
                }
                """.replace("###", postId.toString());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.text").value("Some comment."));


        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].text").value("Some comment 1."))
                .andExpect(jsonPath("$[1].text").value("Some comment 2."))
                .andExpect(jsonPath("$[2].text").value("Some comment 3."))
                .andExpect(jsonPath("$[3].id").isNumber())
                .andExpect(jsonPath("$[3].postId").value(postId))
                .andExpect(jsonPath("$[3].text").value("Some comment."));
    }

    @Test
    void testPutComment() throws Exception {
        String json = """
                {
                    "id": ###id###,
                    "postId": ###postId###,
                    "text": "Some comment 1.(updated)"
                }
                """
                .replace("###id###", commentId.toString())
                .replace("###postId###", postId.toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/{postId}/comments/{id}", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.text").value("Some comment 1.(updated)"));


        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(commentId))
                .andExpect(jsonPath("$[0].postId").value(postId))
                .andExpect(jsonPath("$[0].text").value("Some comment 1.(updated)"));
    }

    @Test
    void testPutCommentReturnsNotFound() throws Exception {
        String json = """
                {
                    "id": ###id###,
                    "postId": ###postId###,
                    "text": "Some comment 1.(updated)"
                }
                """
                .replace("###id###", notExistId.toString())
                .replace("###postId###", postId.toString());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/posts/{postId}/comments/{id}", postId, notExistId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/{postId}/comments/{id}", postId, commentId))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].text").value("Some comment 2."))
                .andExpect(jsonPath("$[1].text").value("Some comment 3."));
    }

    @Test
    void testDeleteCommentReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/{postId}/comments/{id}", postId, notExistId))
                .andExpect(status().isNotFound());
    }
}
