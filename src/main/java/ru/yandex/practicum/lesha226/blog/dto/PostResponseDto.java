package ru.yandex.practicum.lesha226.blog.dto;

import java.util.List;

public record PostResponseDto(
    Long id,
    String title,
    String text,
    List<String> tags,
    int likesCount,
    int commentsCount
) {}
