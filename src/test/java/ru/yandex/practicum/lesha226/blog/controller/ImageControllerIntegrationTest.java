package ru.yandex.practicum.lesha226.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ImageControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;
    private Long postId;
    private final Long notExistPostId = -1L;

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
        postId = jdbcTemplate.queryForObject("""
                select id from posts order by id limit 1""", Long.class);
    }

    @Test
    void testSetImageAndGetImage() throws Exception {
        byte[] imageBody = new byte[]{(byte) 137, 80, 78, 71};
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg",
                MediaType.IMAGE_JPEG_VALUE, imageBody);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PUT,"/posts/{postId}/image", postId)
                        .file(file))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/posts/{postId}/image", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageBody));
    }

    @Test
    void testGetImageReturnsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/posts/{postId}/image", notExistPostId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSetImageReturnsNotFound() throws Exception {
        byte[] imageBody = new byte[]{(byte) 137, 80, 78, 71};
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg",
                MediaType.IMAGE_JPEG_VALUE, imageBody);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PUT,"/posts/{postId}/image", notExistPostId)
                        .file(file))
                .andExpect(status().isNotFound());
    }
}
