package ru.yandex.practicum.lesha226.blog.configuration;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.yandex.practicum.lesha226.blog.configuration.property.CorsProperties;

// TODO : delete this class?
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CorsProperties corsProperties;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (corsProperties.isEnabled()) {
            registry.addMapping("/**")
                    .allowedOrigins(corsProperties.getAllowedOrigins())
                    .allowedMethods(corsProperties.getAllowedMethods())
                    .allowedHeaders(corsProperties.getAllowedHeaders())
                    .allowCredentials(corsProperties.isAllowCredentials())
                    .maxAge(corsProperties.getMaxAge());
        }
    }
}
