package ru.yandex.practicum.lesha226.blog.dto;

import java.util.List;
import java.util.Objects;

public class PostsPageDto {

    private List<PostDto> posts;
    private boolean hasPrev;
    private boolean hasNext;
    private int lastPage;

    public PostsPageDto(List<PostDto> posts, boolean hasPrev, boolean hasNext, int lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostsPageDto postsPageDto = (PostsPageDto) o;
        return hasPrev == postsPageDto.hasPrev && hasNext == postsPageDto.hasNext && lastPage == postsPageDto.lastPage && Objects.equals(posts, postsPageDto.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posts, hasPrev, hasNext, lastPage);
    }

    @Override
    public String toString() {
        return "PostsPage{" +
                "posts=" + posts +
                ", hasPrev=" + hasPrev +
                ", hasNext=" + hasNext +
                ", lastPage=" + lastPage +
                '}';
    }
}
