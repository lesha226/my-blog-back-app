package ru.yandex.practicum.lesha226.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.yandex.practicum.lesha226.blog.configuration.DataSourceConfig;

@Configuration
@Import({DataSourceConfig.class})
@PropertySource("classpath:test-application.properties")
@ComponentScan("ru.yandex.practicum.lesha226.blog.repository")
public class RepositoryTestConfig {
}

