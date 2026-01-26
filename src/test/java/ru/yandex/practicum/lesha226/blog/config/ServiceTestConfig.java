package ru.yandex.practicum.lesha226.blog.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

@Configuration
@ComponentScan("ru.yandex.practicum.lesha226.blog.service")
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
}
