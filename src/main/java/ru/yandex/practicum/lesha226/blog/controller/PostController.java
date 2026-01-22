package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.model.PostsPage;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class PostController {

    private final Post post = new Post(1L, "Title 1", "Some text.", List.of("tag"), 0, 1);
    private final PostsPage postsPage = new PostsPage(List.of(post), false, false, 1);

    @GetMapping
    public PostsPage getPostsPage(
            @RequestParam("search") String search,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1") int pageSize
    ) {
        return postsPage; // TODO: use Service
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable(name = "id") Long id){
        return post; // TODO: use Service
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return this.post; // TODO: use Service
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody Post post) {
        return this.post; // TODO: use Service
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        // TODO: use Service
    }

    @PostMapping("/{id}/likes")
    @ResponseBody
    public int like(@PathVariable(name = "id") Long id) {
        return 1; // TODO: use Service
    }

    @GetMapping("/{id}/image")
    public byte[] getPosts(@PathVariable(name = "id", required = false) Integer id) {
        return new byte[] {}; // TODO: use Service
    }
}
