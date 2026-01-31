package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.model.PostsPage;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostsPage getPostPage(String search, int pageNumber, int pageSize) {
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

        List<Post> postList = repository.findAll(searchTitleString, searchTagList, offset, pageSize);
        int size = repository.size(searchTitleString, searchTagList);

        int lastPage = (size - 1) / pageSize + 1;
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPage(postList, hasPrev, hasNext, lastPage);
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
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
