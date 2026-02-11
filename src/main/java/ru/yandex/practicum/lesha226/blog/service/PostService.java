package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.dto.PostDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.mapper.PostMapper;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.dto.PostsPageDto;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;
import ru.yandex.practicum.lesha226.blog.service.utils.SearchStringParser;

import java.util.List;
import java.util.function.Function;

@Service
public class PostService {

    private final PostRepository repository;
    private final PostMapper mapper = PostMapper.INSTANCE;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostsPageDto getPostPage(String search, int pageNumber, int pageSize) {
        SearchStringParser parser = new SearchStringParser(search);

        int offset = (pageNumber - 1) * pageSize;

        List<Post> postList = repository.findAll(parser.getTitle(), parser.getTagList(), offset, pageSize);
        int size = repository.size(parser.getTitle(), parser.getTagList());

        int lastPage = (size - 1) / pageSize + 1;
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPageDto(mapper.toDto(postList), hasPrev, hasNext, lastPage);
    }

    public PostDto findById(Long id) throws PostNotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public PostDto save(PostDto dto) throws PostNotFoundException {
        Post post = mapper.fromDto(dto);
        Long id = repository.save(post);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PostNotFoundException(post.getId()));
    }

    public PostDto update(PostDto dto) throws PostNotFoundException {
        Post post = mapper.fromDto(dto);
        if (!repository.update(post)) {
            throw new PostNotFoundException(post.getId());
        };
        return repository.findById(post.getId())
                .map(mapper::toDto)
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

}
