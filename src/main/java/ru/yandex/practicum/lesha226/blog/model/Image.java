package ru.yandex.practicum.lesha226.blog.model;

import java.util.Arrays;
import java.util.Objects;

public class Image {
    private Long postId;
    private byte[] body;

    public Image() {
    }

    public Image(Long postId, byte[] body) {
        this.postId = postId;
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(postId, image.postId) && Objects.deepEquals(body, image.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, Arrays.hashCode(body));
    }

    @Override
    public String toString() {
        return "Image{" +
                "postId=" + postId +
                ", body=" + Arrays.hashCode(body) +
                '}';
    }
}
