package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.lesha226.blog.dto.PostCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.PostResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.PostUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.service.mapper.PostMapper;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.dto.PageDto;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;
import ru.yandex.practicum.lesha226.blog.service.utils.SearchStringParser;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository repository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public PageDto getPostPage(String search, int pageNumber, int pageSize) {
        SearchStringParser parser = new SearchStringParser(search);

        int offset = (pageNumber - 1) * pageSize;

        List<Post> postList = repository.findAll(parser.getTitle(), parser.getTagList(), offset, pageSize);
        int size = repository.size(parser.getTitle(), parser.getTagList());

        int lastPage = (size - 1) / pageSize + 1;
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PageDto(mapper.toDto(postList), hasPrev, hasNext, lastPage);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) throws PostNotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public PostResponseDto save(PostCreateDto dto) throws PostNotFoundException {
        Post post = mapper.fromDto(dto);
        Long id = repository.save(post);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public PostResponseDto update(Long id, PostUpdateDto dto) throws PostNotFoundException {
        Post post = repository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        mapper.updateFromDto(dto, post);
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
