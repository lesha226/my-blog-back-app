package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.dto.PostDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.dto.PostsPageDto;
import ru.yandex.practicum.lesha226.blog.service.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public PostsPageDto getPostsPage(
            @RequestParam("search") String search,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1") int pageSize
    ) {
        return service.getPostPage(search, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable(name = "id") Long id) throws PostNotFoundException {
        return service.findById(id);
    }

    @PostMapping
    public PostDto save(@RequestBody PostDto dto) throws PostNotCreateException, PostNotFoundException {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public PostDto update(@RequestBody PostDto dto) throws PostNotFoundException {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) throws PostNotFoundException {
        service.delete(id);
    }

    @PostMapping("/{id}/likes")
    public int like(@PathVariable(name = "id") Long id) throws PostNotFoundException {
        return service.like(id);
    }
}
