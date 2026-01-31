package ru.yandex.practicum.lesha226.blog.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.lesha226.blog.config.ServiceTestConfig;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.model.PostsPage;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(classes = ServiceTestConfig.class)
class PostServiceTest {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostService service;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void testSearchParsing() {
        String search = "word1 #tag1 word2 #tag2";
        String searchTitleString = "word1 word2";
        List<String> tagList = List.of("tag1", "tag2");

        List<Post> postList = List.of(
                new Post(1L, "Title 1", "Some text 1.", tagList, 1, 2),
                new Post(2L, "Title 2", "Some text 2.", tagList, 3, 4));
        PostsPage Page = new PostsPage(postList, false, false, 1);
        PostsPage result;

        when(repository.findAll(searchTitleString, tagList,0, 5)).thenReturn(postList);
        when(repository.size(searchTitleString, tagList)).thenReturn(2);

        result = service.getPostPage(search, 1, 5);

        verify(repository).findAll(searchTitleString, tagList, 0, 5);
        verify(repository).size(searchTitleString, tagList);
        assertEquals(Page, result);
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
                new Post(1L, "Title 1", "Some text 1.", List.of("tag1"), 1, 2),
                new Post(2L, "Title 2", "Some text 2.", List.of("tag1"), 3, 4));
        PostsPage Page = new PostsPage(postList, hasPrev, hasNext, lastPage);
        String searchTitleString = "";
        List<String> tagList = List.of();

        when(repository.findAll(searchTitleString, tagList, offset, size)).thenReturn(postList);
        when(repository.size(searchTitleString, tagList)).thenReturn(5);
        PostsPage result = service.getPostPage(searchTitleString, pageNumber, size);
        verify(repository).findAll(searchTitleString, tagList, offset, size);
        verify(repository).size(searchTitleString, tagList);
        assertEquals(Page, result);
    }

    @Test
    void testFindById() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1", "tag2"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        Post result = service.findById(1L).orElse(null);

        verify(repository).findById(1L);
        assertEquals(post, result);
    }

    @Test
    void testSave() {
        final Post tempPost = new Post(null, "Title", "Some text.", List.of("tag1"), 3, 4);
        final Post resultPost = new Post(null, "Title", "Some text.", List.of("tag1"), 3, 4);

        when(repository.save(tempPost)).thenReturn(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(resultPost));

        Post result = service.save(tempPost).orElse(null);

        verify(repository).save(tempPost);
        verify(repository).findById(1L);
        assertEquals(resultPost, result);
    }

    @Test
    void testUpdate() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        Post result = service.update(post).orElse(null);

        verify(repository).update(post);
        verify(repository).findById(post.getId());
        assertEquals(post, result);
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(repository).delete(1L);
    }

    @Test
    void testLike() {
        final Post post = new Post(1L, "Title", "Some text.", List.of("tag1"), 3, 4);
        when(repository.findById(1L)).thenReturn(Optional.of(post));

        int result = service.like(1L);

        verify(repository).like(1L);
        verify(repository).findById(1L);
        assertEquals(3, result);

    }
}