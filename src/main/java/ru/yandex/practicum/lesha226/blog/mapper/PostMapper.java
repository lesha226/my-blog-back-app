package ru.yandex.practicum.lesha226.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.lesha226.blog.dto.PostDto;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto toDto(Post post);
    List<PostDto> toDto(List<Post> postList);

    Post fromDto(PostDto postDto);

}
