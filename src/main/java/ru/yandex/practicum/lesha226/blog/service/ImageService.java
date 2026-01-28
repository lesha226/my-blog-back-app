package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public Optional<Image> findByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

    public void update(Image image) {
        repository.update(image);
    }
}
