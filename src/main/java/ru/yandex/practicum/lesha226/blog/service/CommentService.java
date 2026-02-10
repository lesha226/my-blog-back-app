package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.exception.NotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.NotFoundException;
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

    public Comment findById(Long id) throws CommentNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public Comment save(Comment comment) throws CommentNotCreateException {
        Long id = repository.save(comment);
        return repository.findById(id)
                .orElseThrow(() -> new CommentNotCreateException(comment));
    }

    public Comment update(Comment comment) throws CommentNotFoundException {
        if (!repository.update(comment)) {
            throw new CommentNotFoundException(comment.getId());
        };

        return repository.findById(comment.getId())
                .orElseThrow(() -> new CommentNotFoundException(comment.getId()));
    }

    public void delete(Long id) throws CommentNotFoundException {
        if (!repository.delete(id)) {
            throw new CommentNotFoundException(id);
        };
    }
}
