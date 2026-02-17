package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.lesha226.blog.dto.PostCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.PostResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.PostUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.dto.PageDto;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;
import ru.yandex.practicum.lesha226.blog.service.mapper.PostMapperImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {PostService.class, PostMapperImpl.class})
class PostServiceTest {
    private final Long id = 1L;
    private final String title = "Some title";
    private final String text = "Some text";
    private final List<String> tags = List.of("tag1", "tag2");
    //private final List<String> tags = new ArrayList<>(List.of("tag1", "tag2"));
    private final Post newPost = new Post(null, title, text, tags, 0, 0);
    private final Post post = new Post(id, title, text, tags, 3, 4);
    private final PostCreateDto createDto = new PostCreateDto(title, text,tags);
    private final PostUpdateDto updateDto = new PostUpdateDto(id, title, text,tags);
    private final PostResponseDto dto = new PostResponseDto(id, title, text, tags, 3, 4);

    @MockitoBean
    private PostRepository repository;

    @Autowired
    private PostService service;

    @Test
    void testSearchParsing() {
        String search = "word1 #tag1 word2 #tag2";
        String searchTitleString = "word1 word2";
        List<String> tagList = List.of("tag1", "tag2");

        when(repository.findAll(searchTitleString, tagList,0, 5)).thenReturn(List.of(post));
        when(repository.size(searchTitleString, tagList)).thenReturn(2);

        service.getPostPage(search, 1, 5);

        verify(repository).findAll(searchTitleString, tagList, 0, 5);
        verify(repository).size(searchTitleString, tagList);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0, 2, false, true, 3",
            "2, 2, 2, true, true, 3",
            "3, 4, 2, true, false, 3",
            "1, 0, 5, false, false, 1"
    })
    void testPageNavigation(int pageNumber, int offset, int size, boolean hasPrev, boolean hasNext, int lastPage) {
        List<Post> postList = List.of(
                new Post(1L, "Title 1", "Some text 1.", tags, 1, 2),
                new Post(2L, "Title 2", "Some text 2.", tags, 3, 4));
        List<PostResponseDto> postResponseDtoList = List.of(
                new PostResponseDto(1L, "Title 1", "Some text 1.", tags, 1, 2),
                new PostResponseDto(2L, "Title 2", "Some text 2.", tags, 3, 4));
        PageDto PageDto = new PageDto(postResponseDtoList, hasPrev, hasNext, lastPage);
        String searchTitleString = "";
        List<String> tagList = List.of();

        when(repository.findAll(searchTitleString, tagList, offset, size)).thenReturn(postList);
        when(repository.size(searchTitleString, tagList)).thenReturn(5);
        PageDto result = service.getPostPage(searchTitleString, pageNumber, size);
        verify(repository).findAll(searchTitleString, tagList, offset, size);
        verify(repository).size(searchTitleString, tagList);
        assertEquals(PageDto, result);
    }

    @Test
    void testFindById() throws PostNotFoundException {
        when(repository.findById(id)).thenReturn(Optional.of(post));

        PostResponseDto result = service.findById(id);

        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testFindByIdThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            PostResponseDto result = service.findById(id);
        });
    }

    @Test
    void testSave() throws PostNotFoundException {
        when(repository.save(newPost)).thenReturn(id);
        when(repository.findById(id)).thenReturn(Optional.of(post));

        PostResponseDto result = service.save(createDto);

        verify(repository).save(newPost);
        verify(repository).findById(id);
        assertEquals(dto, result);
    }

    @Test
    void testUpdate() throws PostNotFoundException {
        when(repository.update(post)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(post)).thenReturn(Optional.of(post));

        PostResponseDto result = service.update(id, updateDto);

        verify(repository).update(post);
        verify(repository, times(2)).findById(post.getId());
        assertEquals(dto, result);
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void testUpdateThrowException(boolean isUpdated) {
        when(repository.findById(post.getId())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            PostResponseDto result = service.update(id, updateDto);
        });
    }

    @Test
    void testDelete() throws PostNotFoundException {
        when(repository.delete(id)).thenReturn(true);

        service.delete(id);

        verify(repository).delete(id);
    }

    @Test
    void testDeleteThrowException() {
        when(repository.delete(id)).thenReturn(false);

        assertThrows(PostNotFoundException.class, () -> {
            service.delete(id);
        });
    }

    @Test
    void testLike() throws PostNotFoundException {
        when(repository.findById(id)).thenReturn(Optional.of(post));

        int result = service.like(id);

        verify(repository).like(id);
        verify(repository).findById(id);
        assertEquals(post.getLikesCount(), result);

    }

    @Test
    void testLikeThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> {
            int result = service.like(id);
        });
    }
}