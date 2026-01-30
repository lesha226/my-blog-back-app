package ru.yandex.practicum.lesha226.blog.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.lesha226.blog.model.Image;
import ru.yandex.practicum.lesha226.blog.repository.ImageRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Repository
public class JdbcNativeImageRepository implements ImageRepository {

    private final JdbcTemplate template;

    private final RowMapper<Image> mapper = (rs, rowNum) -> {
        try {
            return new Image(
                    rs.getLong("post_id"),
                    rs.getBlob("body").getBinaryStream().readAllBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public JdbcNativeImageRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Optional<Image> findByPostId(Long postId) {
        try {
            Image image = template.queryForObject("""
                    select post_id, body
                    from images
                    where post_id = ?""", mapper, postId);
            return Optional.ofNullable(image);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Image image) {
        ByteArrayInputStream bodyStream = new ByteArrayInputStream(image.getBody());
        template.update("""
                merge into images(post_id, body)
                values (?, ?)""", image.getPostId(), bodyStream);
    }
}
