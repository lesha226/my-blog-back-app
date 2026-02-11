package ru.yandex.practicum.lesha226.blog.dto;


import ru.yandex.practicum.lesha226.blog.model.Comment;

public record CommentDto (
    Long id,
    Long postId,
    String text
) {}
