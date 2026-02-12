package ru.yandex.practicum.lesha226.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateDto(
        @NotBlank(message = "Пустой текст")
        String text,

        @NotNull
        Long postId
) {}
