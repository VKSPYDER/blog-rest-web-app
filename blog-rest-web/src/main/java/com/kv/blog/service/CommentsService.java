package com.kv.blog.service;

import com.kv.blog.dto.Comment;

import java.util.List;

public interface CommentsService {

    List<Comment> getAllCommentsFromAllUsers();

    List<Comment> getCommentsForThePost(Long postId);

}
