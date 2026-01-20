package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.model.PostsPage;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class PostController {

    @GetMapping
    public PostsPage getPosts(
            @PathVariable(name = "search", required = false) String search,
            @PathVariable(name = "pageNumber", required = false) Integer pageNumber,
            @PathVariable(name = "pageSize", required = false) Integer pageSize
    ) {
        return new PostsPage(
                List.of(new Post(
                        1L,
                        "Post title #1",
                        "Post text #1",
                        List.of("tag1", "tag2"),
                        0,
                        0)),
                false,
                false,
                1); // TODO: use Service
    }

    @GetMapping("/{id}/image")
    public byte[] getPosts(@PathVariable(name = "id", required = false) Integer id) {
        return new byte[] {}; // TODO: use Service
    }
}
