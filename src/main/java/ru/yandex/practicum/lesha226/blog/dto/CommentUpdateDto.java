package ru.yandex.practicum.lesha226.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentUpdateDto(
        @NotNull
        Long id,

        @NotBlank(message = "Text is blank")
        String text,

        @NotNull
        Long postId
) {}
