package ru.yandex.practicum.lesha226.blog.model;

import java.util.List;
import java.util.Objects;

public class Post {

    private Long id;
    private String title;
    private String text;
    private List<String> tags;
    private int likesCount;
    private int commentsCount;

    public Post() {
    }

    public Post(Long id, String title, String text, List<String> tags, int likesCount, int commentsCount) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return likesCount == post.likesCount && commentsCount == post.commentsCount && Objects.equals(title, post.title) && Objects.equals(text, post.text) && Objects.equals(tags, post.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text, tags, likesCount, commentsCount);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", tags=" + tags +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                '}';
    }
}
