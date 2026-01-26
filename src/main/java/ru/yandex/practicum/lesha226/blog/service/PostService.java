package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> findAll(String search, int offset, int size) {
        // TODO : search
        return repository.findAll(offset, size);
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public int size(String search) {
        // TODO : search
        return repository.size();
    }

    public Optional<Post> save(Post post) {
        Long id = repository.save(post);
        return repository.findById(id);
    }

    public Optional<Post> update(Post post) {
        repository.update(post);
        return repository.findById(post.getId());
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public int like(Long id) {
        repository.like(id);
        return repository.findById(id).map(Post::getLikesCount).orElse(0);
    }


}
