package ru.yandex.practicum.lesha226.blog.service.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchStringParserTest {

    @Test
    void test() {
        String search = "     word1    #tag1 word2 #tag2 # ";
        String searchTitleString = "word1 word2";
        List<String> tagList = List.of("tag1", "tag2");

        SearchStringParser result = new SearchStringParser(search);

        assertEquals(searchTitleString, result.getTitle());
        assertEquals(tagList, result.getTagList());
    }

}