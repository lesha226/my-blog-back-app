package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.dto.PostDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.dto.PostsPageDto;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostsPageDto getPostPage(String search, int pageNumber, int pageSize) {
        List<String> searchWordList = new ArrayList<>();
        List<String> searchTagList = new ArrayList<>();
        for (String word: search.split(" ")) {
            if (word.startsWith("#")) {
                searchTagList.addLast(word.substring(1));
            } else if (!word.isEmpty()) {
                searchWordList.addLast(word);
            }
        }
        String searchTitleString = String.join(" ", searchWordList);

        int offset = (pageNumber - 1) * pageSize;

        List<PostDto> postList = repository.findAll(searchTitleString, searchTagList, offset, pageSize)
                .stream().map(mapper).toList();
        int size = repository.size(searchTitleString, searchTagList);

        int lastPage = (size - 1) / pageSize + 1;
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPageDto(postList, hasPrev, hasNext, lastPage);
    }

    public PostDto findById(Long id) throws PostNotFoundException {
        return repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public PostDto save(PostDto dto) throws PostNotFoundException {
        Post post = fromDto(dto);
        Long id = repository.save(post);
        return repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new PostNotFoundException(post.getId()));
    }

    public PostDto update(PostDto dto) throws PostNotFoundException {
        Post post = fromDto(dto);
        if (!repository.update(post)) {
            throw new PostNotFoundException(post.getId());
        };
        return repository.findById(post.getId())
                .map(mapper)
                .orElseThrow(() -> new PostNotFoundException(post.getId()));
    }

    public void delete(Long id) throws PostNotFoundException {
        if (!repository.delete(id)) {
            throw new PostNotFoundException(id);
        };
    }

    public int like(Long id) throws PostNotFoundException {
        repository.like(id);
        return repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id))
                .getLikesCount();
    }

    private static final Function<Post, PostDto> mapper = post -> {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getText(),
                post.getTags(),
                post.getLikesCount(),
                post.getCommentsCount());
    };

    private static Post fromDto(PostDto dto) {
        return new Post(dto.id(), dto.title(), dto.text(), dto.tags(), dto.likesCount(), dto.commentsCount());
    }



}
