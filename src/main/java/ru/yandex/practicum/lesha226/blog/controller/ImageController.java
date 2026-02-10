package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.lesha226.blog.exception.ImageNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/posts/{postId}/image")
@CrossOrigin(origins = "http://localhost")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(name = "postId") Long postId) throws ImageNotFoundException {
        return service.findByPostId(postId).getBody();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateImage(@PathVariable("postId") Long postId, @RequestParam("image") MultipartFile file) throws ImageNotFoundException {
        if (!file.isEmpty()) {
            byte[] body;
            try {
                body = file.getBytes();
            } catch (IOException e) {
                body = new byte[] {};
            }
            Image image = new Image(postId, body);

            service.update(image);
        }
    }
}
