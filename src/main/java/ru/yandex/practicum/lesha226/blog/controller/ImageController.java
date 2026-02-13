package ru.yandex.practicum.lesha226.blog.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.lesha226.blog.exception.ImageNotFoundException;
import ru.yandex.practicum.lesha226.blog.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/posts/{postId}/image")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(@PathVariable(name = "postId") Long postId) throws ImageNotFoundException, IOException {
        byte[] buf = service.findByPostId(postId);
        Resource file = new ByteArrayResource(buf);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(file.contentLength())
                .body(file);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> putImage(@PathVariable("postId") Long postId,
                                           @RequestParam("image") MultipartFile file) throws ImageNotFoundException, IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        byte[] body = file.getBytes();
        service.update(postId, body);

        return ResponseEntity.ok("Ok");
    }
}
