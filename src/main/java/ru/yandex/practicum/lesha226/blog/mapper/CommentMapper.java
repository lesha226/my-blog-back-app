package ru.yandex.practicum.lesha226.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.lesha226.blog.dto.CommentDto;
import ru.yandex.practicum.lesha226.blog.model.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comment comment);
    List<CommentDto> toDto(List<Comment> commentList);

    Comment fromDto(CommentDto dto);

}
