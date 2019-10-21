package com.kv.blog.service;

import com.kv.blog.dto.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPostsForAllUsers();

    List<Post> getUserPosts(long userId, int after, int limit);
}
