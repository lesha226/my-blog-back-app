package ru.yandex.practicum.lesha226.blog.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
    private boolean enabled = false;
    private String[] mapping = {"/**"};
    private String[] allowedOrigins = {"http://localhost"};
    private String[] allowedMethods = "GET,POST,PUT,DELETE,OPTIONS,HEAD".split(",");
    private String[] allowedHeaders = {"*"};
    private boolean allowCredentials = false;
    private long maxAge = -1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getMapping() {
        return mapping;
    }

    public void setMapping(String[] mapping) {
        this.mapping = mapping;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(String[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "CorsProperties{" +
                "enabled=" + enabled +
                ", mapping=" + Arrays.toString(mapping) +
                ", allowedOrigins=" + Arrays.toString(allowedOrigins) +
                ", allowedMethods=" + Arrays.toString(allowedMethods) +
                ", allowedHeaders=" + Arrays.toString(allowedHeaders) +
                ", allowCredentials=" + allowCredentials +
                ", maxAge=" + maxAge +
                '}';
    }
}
