package ru.yandex.practicum.lesha226.blog.exception;

import ru.yandex.practicum.lesha226.blog.model.Comment;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(Long id) {
        super("Комментарий не найден(id: " + id + ").");
    }

}
