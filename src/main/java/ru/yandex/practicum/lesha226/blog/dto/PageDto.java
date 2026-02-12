package ru.yandex.practicum.lesha226.blog.dto;

import java.util.List;
import java.util.Objects;

public class PageDto {

    private List<PostResponseDto> posts;
    private boolean hasPrev;
    private boolean hasNext;
    private int lastPage;

    public PageDto(List<PostResponseDto> posts, boolean hasPrev, boolean hasNext, int lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    public List<PostResponseDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponseDto> posts) {
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
    public String toString() {
        return "PageDto{" +
                "posts=" + posts +
                ", hasPrev=" + hasPrev +
                ", hasNext=" + hasNext +
                ", lastPage=" + lastPage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PageDto pageDto = (PageDto) o;
        return hasPrev == pageDto.hasPrev && hasNext == pageDto.hasNext && lastPage == pageDto.lastPage && Objects.equals(posts, pageDto.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posts, hasPrev, hasNext, lastPage);
    }
}
