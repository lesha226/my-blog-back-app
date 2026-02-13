package ru.yandex.practicum.lesha226.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostCreateDto(
        @NotBlank(message = "Title is blank")
        @Size(max = 50)
        String title,

        @NotBlank(message = "Title is blank")
        String text,

        @NotNull
        List<String> tags
) {
}
