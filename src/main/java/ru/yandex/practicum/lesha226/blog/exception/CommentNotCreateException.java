package ru.yandex.practicum.lesha226.blog.exception;

import ru.yandex.practicum.lesha226.blog.model.Comment;

public class CommentNotCreateException extends RuntimeException {

    public CommentNotCreateException(Comment comment) {
        super("Ошибка при добавлении комментария(comment: " + comment+ ").");
    }

}
