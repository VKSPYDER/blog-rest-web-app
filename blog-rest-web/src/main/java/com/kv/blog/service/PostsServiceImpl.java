package com.kv.blog.service;

import com.kv.blog.dto.Post;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kv.blog.utils.BlogResponseUtils.handleResponseCodes;

@Service
public class PostsServiceImpl implements PostService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${service.get.posts.url}")
    private String urlForUserPosts;

    @Autowired
    private RestTemplate template;

    @Override
    public List<Post> getAllPostsForAllUsers() {
        log.info("Accessing All the Users and their Posts from URL : {}", urlForUserPosts);
        try {
            ResponseEntity<List<Post>> response = template.exchange(
                    urlForUserPosts,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Post>>() {
                    });
            handleResponseCodes(response);
            return response.getBody();
        } catch (Exception e) {
            throw new ResourceUnavailableException(e);
        }
    }

    @Override
    public List<Post> getUserPosts(long userId, int after, int limit) {
        log.info("Returning {} posts after post ID:{}, from the user :{}", limit, after, userId);
        return getAllPostsForAllUsers().stream().filter(p -> p.getUserId() == userId)
                .sorted(Comparator.comparing(Post::getId)).
                        filter(p -> p.getId() > after).limit(limit < 0 ? 0 : limit).collect(Collectors.toList());
    }

}
