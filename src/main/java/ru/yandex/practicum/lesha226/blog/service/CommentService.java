package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.dto.CommentDto;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.mapper.CommentMapper;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper = CommentMapper.INSTANCE;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<CommentDto> findAll(Long postId) {
        List<Comment> commentList = repository.findAllByPostId(postId);

        return mapper.toDto(commentList);
    }

    public CommentDto findById(Long id) throws CommentNotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public CommentDto save(CommentDto commentDto) throws CommentNotCreateException {
        Comment comment = mapper.fromDto(commentDto);

        Long id = repository.save(comment);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotCreateException(comment));
    }

    public CommentDto update(CommentDto commentDto) throws CommentNotFoundException {
        Comment comment = mapper.fromDto(commentDto);
        if (!repository.update(comment)) {
            throw new CommentNotFoundException(comment.getId());
        };

        return repository.findById(comment.getId())
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotFoundException(comment.getId()));
    }

    public void delete(Long id) throws CommentNotFoundException {
        if (!repository.delete(id)) {
            throw new CommentNotFoundException(id);
        };
    }

}
