package com.kv.blog.controller;

import com.kv.blog.dto.Post;
import com.kv.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserPostsController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    PostService postsServiceImpl;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getPostForUserId(@RequestParam(value = "userId", defaultValue = "0") long userId, @RequestParam(value = "after", defaultValue = "0") int after, @RequestParam(value = "limit", defaultValue = "3") int limit) {
        log.info("Accessing the Post details for {}", userId);
        return postsServiceImpl.getUserPosts(userId, after, limit);
    }
}
