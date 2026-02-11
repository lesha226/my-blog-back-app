package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.dto.CommentDto;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import java.util.List;
import java.util.function.Function;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<CommentDto> findAll(Long postId) {
        return repository.findAllByPostId(postId)
                .stream().map(mapper).toList();
    }

    public CommentDto findById(Long id) throws CommentNotFoundException {
        return repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    public CommentDto save(CommentDto commentDto) throws CommentNotCreateException {
        Comment comment = fromDto(commentDto);

        Long id = repository.save(comment);
        return repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new CommentNotCreateException(comment));
    }

    public CommentDto update(CommentDto commentDto) throws CommentNotFoundException {
        Comment comment = fromDto(commentDto);
        if (!repository.update(comment)) {
            throw new CommentNotFoundException(comment.getId());
        };

        return repository.findById(comment.getId())
                .map(mapper)
                .orElseThrow(() -> new CommentNotFoundException(comment.getId()));
    }

    public void delete(Long id) throws CommentNotFoundException {
        if (!repository.delete(id)) {
            throw new CommentNotFoundException(id);
        };
    }

    private static final Function<Comment, CommentDto> mapper = comment -> new CommentDto(
            comment.getId(), comment.getPostId(), comment.getText());
    private static final Function<CommentDto, Comment> fromDtoMapper = commentDto -> new Comment(
            commentDto.id(), commentDto.postId(), commentDto.text());

    private static Comment fromDto(CommentDto dto) {
        return new Comment(
                dto.id(),
                dto.postId(),
                dto.text()
        );
    }
}
