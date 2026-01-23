package ru.yandex.practicum.lesha226.blog.repository;

import ru.yandex.practicum.lesha226.blog.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findById(Long id);

    Long save(Comment comment);
    void update(Comment comment);
    void delete(Long id);
}
