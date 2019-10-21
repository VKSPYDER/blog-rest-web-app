package com.kv.blog.service;

import com.kv.blog.dto.Comment;
import com.kv.blog.errors.ResourceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.kv.blog.utils.BlogResponseUtils.handleResponseCodes;

@Service
public class CommentsServiceImpl implements CommentsService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentsServiceImpl.class);

    @Value("${service.get.comments.url}")
    private String urlForUserCommentsOnPosts;

    @Autowired
    private RestTemplate template;

    @Override
    public List<Comment> getAllCommentsFromAllUsers() {

        LOG.info("Accessing All the Comments from all the Users for all Posts from URL : {}", urlForUserCommentsOnPosts);
        try {
            ResponseEntity<List<Comment>> response = template.exchange(
                    urlForUserCommentsOnPosts,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Comment>>() {
                    });
            handleResponseCodes(response);
            return response.getBody();
        } catch (Exception e) {
            throw new ResourceUnavailableException(e);
        }
    }

    @Override
    public List<Comment> getCommentsForThePost(Long postId) {
        LOG.info("Accessing the Comments for Post : {}", postId);
        return getAllCommentsFromAllUsers().stream().filter(c -> (c.getPostId() == postId)).collect(Collectors.toList());
    }

}
