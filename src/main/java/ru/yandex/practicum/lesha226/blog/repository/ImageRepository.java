package ru.yandex.practicum.lesha226.blog.repository;

import ru.yandex.practicum.lesha226.blog.model.Image;

import java.util.Optional;

public interface ImageRepository {

    Optional<Image> findByPostId(Long postId);

    boolean update(Image image);
}
