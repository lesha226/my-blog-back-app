package ru.yandex.practicum.lesha226.blog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.yandex.practicum.lesha226.blog.dto.CommentResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.CommentUpdateDto;
import ru.yandex.practicum.lesha226.blog.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponseDto toDto(Comment comment);
    List<CommentResponseDto> toDto(List<Comment> commentList);

    @Mapping(target = "id", ignore = true)
    Comment fromDto(CommentCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postId", ignore = true)
    void updateFromDto(CommentUpdateDto dto, @MappingTarget Comment comment);

}
