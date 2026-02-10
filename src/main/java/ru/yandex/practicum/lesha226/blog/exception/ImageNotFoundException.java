package ru.yandex.practicum.lesha226.blog.exception;

public class ImageNotFoundException extends NotFoundException {
    public ImageNotFoundException(Long postId) {
        super("Изображение не найдено(postId: " + postId + ")");
    }
}
