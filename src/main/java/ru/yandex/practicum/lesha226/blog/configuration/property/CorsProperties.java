package ru.yandex.practicum.lesha226.blog.configuration.property;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CorsProperties {
    private final String[] allowedOrigins;
    private final String[] allowedMethods;
    private final String[] allowedHeaders;
    private final boolean allowCredentials;
    private final long maxAge;

    public CorsProperties(Environment environment) {
        String defaultAllowedOrigins = "http://localhost";
        String defaultAllowedMethods = "GET,POST,PUT,DELETE,OPTIONS,HEAD";
        String defaultAllowedHeaders = "*";
        Boolean defaultAllowCredentials = false;
        Long defaultMaxAge = -1L;

        allowedOrigins = environment.getProperty( "app.cors.allowed-origins", defaultAllowedOrigins).split(",");
        allowedMethods = environment.getProperty("app.cors.allowed-methods", defaultAllowedMethods).split(",");
        allowedHeaders = environment.getProperty("app.cors.allowed-headers", defaultAllowedHeaders).split(",");
        allowCredentials = environment.getProperty("app.cors.allow-credentials", Boolean.class, defaultAllowCredentials);
        maxAge = environment.getProperty("app.cors.max-age", Long.class, defaultMaxAge);
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public long getMaxAge() {
        return maxAge;
    }

    @Override
    public String toString() {
        return "CorsProperties{" +
                "allowedOrigins=" + Arrays.toString(allowedOrigins) +
                ", allowedMethods=" + Arrays.toString(allowedMethods) +
                ", allowedHeaders=" + Arrays.toString(allowedHeaders) +
                ", allowCredentials=" + allowCredentials +
                ", maxAge=" + maxAge +
                '}';
    }
}
