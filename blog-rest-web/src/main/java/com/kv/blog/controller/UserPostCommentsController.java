package com.kv.blog.controller;

import com.kv.blog.dto.Comment;
import com.kv.blog.service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserPostCommentsController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    CommentsService userCommentsService;

    @RequestMapping(value = "/all-comments", method = RequestMethod.GET)
    public List<Comment> home() {
        log.info("Accessing all the User Comments for all the posts");
        return userCommentsService.getAllCommentsFromAllUsers();
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public List<Comment> getUserCommentsForPost(@RequestParam(value = "postId", defaultValue = "0") long postId) {
        log.info("Accessing the Comment details for post {}", postId);
        return userCommentsService.getCommentsForThePost(postId);
    }
}
