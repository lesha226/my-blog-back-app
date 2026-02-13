package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.lesha226.blog.dto.CommentCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.CommentNotFoundException;
import ru.yandex.practicum.lesha226.blog.service.mapper.CommentMapper;
import ru.yandex.practicum.lesha226.blog.model.Comment;
import ru.yandex.practicum.lesha226.blog.repository.CommentRepository;

import javax.sql.DataSource;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    public CommentService(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long postId) {
        List<Comment> commentList = repository.findAllByPostId(postId);

        return mapper.toDto(commentList);
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) throws CommentNotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public CommentResponseDto save(CommentCreateDto dto) throws CommentNotCreateException {
        Comment comment = mapper.fromDto(dto);

        Long id = repository.save(comment);

        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotCreateException(comment));
    }


    public CommentResponseDto update(Long id, CommentUpdateDto dto) throws CommentNotFoundException {
        Comment comment = repository.findById(dto.id())
                .orElseThrow(() -> new CommentNotFoundException(id));

        mapper.updateFromDto(dto, comment);
        if (!repository.update(comment)) {
            throw new CommentNotFoundException(comment.getId());
        }

        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) throws CommentNotFoundException {
        if (!repository.delete(id)) {
            throw new CommentNotFoundException(id);
        }
    }

}
