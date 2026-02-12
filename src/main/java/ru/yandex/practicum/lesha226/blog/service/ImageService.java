package ru.yandex.practicum.lesha226.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public byte[] findByPostId(Long postId) throws ImageNotFoundException {
        return repository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException(postId))
                .getBody();
    }

    @Transactional
    public void update(Long postId, byte[] body) throws ImageNotFoundException {
        Image image = new Image(postId, body);

        if (!repository.update(image)) {
            throw new ImageNotFoundException(image.getPostId());
        };
    }
}
