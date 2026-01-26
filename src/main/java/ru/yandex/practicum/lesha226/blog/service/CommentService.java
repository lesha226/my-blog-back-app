package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll(Long postId) {
        return repository.findAllByPostId(postId);
    }

    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Comment> save(Comment comment) {
        Long id = repository.save(comment);
        return repository.findById(id);
    }

    public Optional<Comment> update(Comment comment) {
        repository.update(comment);
        return repository.findById(comment.getId());
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
