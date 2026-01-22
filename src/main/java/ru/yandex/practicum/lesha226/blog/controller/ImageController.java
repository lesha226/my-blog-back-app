package ru.yandex.practicum.lesha226.blog.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{id}")
@CrossOrigin(origins = "http://localhost")
public class ImageController {

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable(name = "id") Long id) {
        return new byte[] {}; // TODO: use Service
    }

    @PutMapping("/image")
    public void updateImage(@PathVariable(name = "id") Long id) {
        // TODO : use Service
    }
}
