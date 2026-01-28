package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.service.ImageService;

import java.util.Optional;

@RestController
@RequestMapping("/posts/{id}")
@CrossOrigin(origins = "http://localhost")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(name = "id") Long id) {
        Image image = service.findByPostId(id).orElse(null);
        if (image != null) {
            return image.getBody();
        } else {
            return new byte[] {};
        }
    }

    @PutMapping("/image")
    public void updateImage(@PathVariable(name = "id") Long id, @RequestBody byte[] body) {
        Image image = new Image(id, body);
        service.update(image);
    }
}
