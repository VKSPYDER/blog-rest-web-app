package com.kv.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Long userId;
    private Long id;
    private String title;
    private String body;

    public Post(Long userId, Long id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;

        Post post = (Post) o;

        if (!getUserId().equals(post.getUserId())) return false;
        if (!getId().equals(post.getId())) return false;
        if (getTitle() != null ? !getTitle().equals(post.getTitle()) : post.getTitle() != null) return false;
        return getBody() != null ? getBody().equals(post.getBody()) : post.getBody() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }
}
