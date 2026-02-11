package ru.yandex.practicum.lesha226.blog.service.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SearchStringParser {
    private final String title;
    private final List<String> tagList;

    public SearchStringParser(String search) {
        if (search == null || search.isBlank()) {
            this.title = "";
            this.tagList = List.of();
            return;
        }

        List<String> wordList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        for (String word: search.split("\\s+")) {
            if (word.startsWith("#")) {
                if (word.length() > 1) {
                    tagList.addLast(word.substring(1));
                }
            } else if (!word.isEmpty()) {
                wordList.addLast(word);
            }
        }
        this.title = String.join(" ", wordList);
        this.tagList = Collections.unmodifiableList(tagList);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTagList() {
        return tagList;
    }
}

