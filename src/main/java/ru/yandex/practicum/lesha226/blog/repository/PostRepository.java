package ru.yandex.practicum.lesha226.blog.repository;

import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> findAll(int offset, int size);

    Optional<Post>  findById(Long id);

    int size();

    Long save(Post post);

    void update(Post post);

    void delete(Long id);

    void like(Long id);
}
