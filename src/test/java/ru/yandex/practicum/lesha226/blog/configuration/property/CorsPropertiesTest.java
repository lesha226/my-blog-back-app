package ru.yandex.practicum.lesha226.blog.configuration.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CorsPropertiesTest {

    @Autowired
    CorsProperties corsProperties;

    @Test
    void test() {
        assertFalse(corsProperties.isEnabled());
        assertArrayEquals(new String[] {"temp-getMapping"}, corsProperties.getMapping());
        assertArrayEquals(new String[] {"temp-getAllowedOrigins"}, corsProperties.getAllowedOrigins());
        assertArrayEquals(new String[] {"temp-getAllowedMethods"}, corsProperties.getAllowedMethods());
        assertArrayEquals(new String[] {"temp-getAllowedHeaders"}, corsProperties.getAllowedHeaders());
        assertTrue(corsProperties.isAllowCredentials());
        assertEquals(-123, corsProperties.getMaxAge());
    }
}