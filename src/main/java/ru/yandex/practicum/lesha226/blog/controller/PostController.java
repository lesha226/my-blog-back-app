package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.model.PostsPage;
import ru.yandex.practicum.lesha226.blog.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public PostsPage getPostsPage(
            @RequestParam("search") String search,
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "1") int pageSize
    ) {
        int offset = (pageNumber - 1) * pageSize;

        List<Post> postList = service.findAll(search, offset, pageSize);
        int size = service.size(search);

        int lastPage = (size - 1) / pageSize + 1;
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;

        return new PostsPage(postList, hasPrev, hasNext, lastPage);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable(name = "id") Long id){
        return service.findById(id).orElse(null);
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post).orElse(null);
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody Post post) {
        return service.update(post).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/likes")
    @ResponseBody
    public int like(@PathVariable(name = "id") Long id) {
        return service.like(id);
    }
}
