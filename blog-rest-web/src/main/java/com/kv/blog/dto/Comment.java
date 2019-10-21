package com.kv.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable {
    private Long postId;
    private Long id;
    private String name;
    private String email;
    private String body;

    public Comment(Long postId, Long id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comment() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (!getPostId().equals(comment.getPostId())) return false;
        if (!getId().equals(comment.getId())) return false;
        if (getName() != null ? !getName().equals(comment.getName()) : comment.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(comment.getEmail()) : comment.getEmail() != null) return false;
        return getBody() != null ? getBody().equals(comment.getBody()) : comment.getBody() == null;
    }

    @Override
    public int hashCode() {
        int result = getPostId().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }
}
