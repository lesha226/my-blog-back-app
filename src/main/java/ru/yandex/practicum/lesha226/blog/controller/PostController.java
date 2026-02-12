package ru.yandex.practicum.lesha226.blog.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.dto.PostCreateDto;
import ru.yandex.practicum.lesha226.blog.dto.PostResponseDto;
import ru.yandex.practicum.lesha226.blog.dto.PostUpdateDto;
import ru.yandex.practicum.lesha226.blog.exception.PostNotCreateException;
import ru.yandex.practicum.lesha226.blog.exception.PostNotFoundException;
import ru.yandex.practicum.lesha226.blog.dto.PageDto;
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
    public PageDto getPostsPage(
            @RequestParam("search") String search,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1") int pageSize
    ) {
        return service.getPostPage(search, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long id) throws PostNotFoundException {
        return service.findById(id);
    }

    @PostMapping
    public PostResponseDto save(@Valid @RequestBody PostCreateDto dto) throws PostNotCreateException, PostNotFoundException {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public PostResponseDto update(@PathVariable("id") Long id,
                                  @Valid @RequestBody PostUpdateDto dto) throws PostNotFoundException {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws PostNotFoundException {
        service.delete(id);
    }

    @PostMapping("/{id}/likes")
    public int like(@PathVariable("id") Long id) throws PostNotFoundException {
        return service.like(id);
    }

    /*@GetMapping("/test")
    public String testEncoding() {
        return "Привет, мир! UTF-8 работает.";
    }*/
}
