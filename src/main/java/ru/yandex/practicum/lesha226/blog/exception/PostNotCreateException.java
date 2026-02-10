package ru.yandex.practicum.lesha226.blog.exception;

import ru.yandex.practicum.lesha226.blog.model.Post;

public class PostNotCreateException extends NotCreateException {
    public PostNotCreateException(Post post) {
        super("Ошибка при добавлении поста(post: " + post + ").");
    }
}
