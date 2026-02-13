package ru.yandex.practicum.lesha226.blog.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.yandex.practicum.lesha226.blog.dto.PostCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.PostResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.PostUpdateDto;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "tags", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    PostResponseDto toDto(Post post);

    @Mapping(target = "tags", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    List<PostResponseDto> toDto(List<Post> postList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    //@Mapping(target = "tags", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Post fromDto(PostCreateDto dto);

    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    @Mapping(target = "tags", source = "tags", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)*/
    default void updateFromDto(PostUpdateDto dto, @MappingTarget Post post) {
        if ( dto == null ) {
            return;
        }

        post.setTitle( dto.title() );
        post.setText( dto.text() );
        post.setTags(dto.tags());
    }
}
