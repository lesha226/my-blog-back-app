package ru.yandex.practicum.lesha226.blog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.yandex.practicum.lesha226.blog.configuration.DataSourceConfig;
import ru.yandex.practicum.lesha226.blog.configuration.MultipartConfiguration;
import ru.yandex.practicum.lesha226.blog.configuration.RestConfig;

@Configuration
@EnableWebMvc()
@Import({
        DataSourceConfig.class,
        MultipartConfiguration.class,
        RestConfig.class})
@ComponentScan(basePackages = {
        "ru.yandex.practicum.lesha226.blog.service",
        "ru.yandex.practicum.lesha226.blog.controller",
        "ru.yandex.practicum.lesha226.blog.repository"})
/*@ComponentScan(basePackages = {
        "ru.yandex.practicum.lesha226.blog"})*/
public class IntegrationTestConfig {
}



