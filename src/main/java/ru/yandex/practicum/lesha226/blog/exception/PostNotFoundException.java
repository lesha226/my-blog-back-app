package ru.yandex.practicum.lesha226.blog.exception;

import ru.yandex.practicum.lesha226.blog.model.Post;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(Long id) {
        super("Пост не найден(id: " + id + ")");
    }
}
