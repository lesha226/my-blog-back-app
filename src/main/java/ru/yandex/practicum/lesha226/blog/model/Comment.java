package ru.yandex.practicum.lesha226.blog.model;

import java.util.Objects;

public class Comment {
    private Long id;
    private Long postId;
    private String text;

    public Comment() {}

    public Comment(Long id, Long postId, String text) {
        this.id = id;
        this.postId = postId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(postId, comment.postId) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, text);
    }
}
