package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.exception.ImageNotFoundException;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public Image findByPostId(Long postId) throws ImageNotFoundException {
        return repository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException(postId));
    }

    public void update(Image image) throws ImageNotFoundException {
        if (!repository.update(image)) {
            throw new ImageNotFoundException(image.getPostId());
        };
    }
}
