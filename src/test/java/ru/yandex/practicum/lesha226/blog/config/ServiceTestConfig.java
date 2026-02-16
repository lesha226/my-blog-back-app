package ru.yandex.practicum.lesha226.blog.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

@TestConfiguration()
@ComponentScan("ru.yandex.practicum.lesha226.blog.service")
@ActiveProfiles("service-test")
public class ServiceTestConfig {

    @Bean
    @Primary
    public CommentRepository mockCommentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    @Primary
    public PostRepository mockPostRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    @Primary
    public ImageRepository mockImageRepository() {
        return Mockito.mock(ImageRepository.class);
    }
}
