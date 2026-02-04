package ru.yandex.practicum.lesha226.blog.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.lesha226.blog.config.IntegrationTestConfig;


import java.io.ByteArrayInputStream;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitConfig(classes = {IntegrationTestConfig.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public class ImageControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mockMvc;
    private Long postId;

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
    void testImage() throws Exception {
        byte[] imageBody = new byte[]{(byte) 137, 80, 78, 71};
        MockMultipartFile file = new MockMultipartFile("image", "image.jpg",
                MediaType.IMAGE_JPEG_VALUE, imageBody);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.PUT,"/posts/{postId}/image", postId)
                        .file(file))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/posts/{postId}/image", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageBody));
    }
}
