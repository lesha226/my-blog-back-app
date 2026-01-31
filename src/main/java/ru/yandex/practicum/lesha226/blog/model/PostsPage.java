package ru.yandex.practicum.lesha226.blog.model;

import java.util.List;
import java.util.Objects;

public class PostsPage {

    private List<Post> posts;
    private boolean hasPrev;
    private boolean hasNext;
    private int lastPage;

    public PostsPage(List<Post> posts, boolean hasPrev, boolean hasNext, int lastPage) {
        this.posts = posts;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;
        this.lastPage = lastPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
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
        PostsPage postsPage = (PostsPage) o;
        return hasPrev == postsPage.hasPrev && hasNext == postsPage.hasNext && lastPage == postsPage.lastPage && Objects.equals(posts, postsPage.posts);
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
