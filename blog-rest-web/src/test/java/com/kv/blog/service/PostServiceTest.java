package com.kv.blog.service;

import com.kv.blog.BlogTestUtils;
import com.kv.blog.dto.Post;
import com.kv.blog.errors.ResourceUnavailableException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@RestClientTest({PostsServiceImpl.class})
@AutoConfigureWebClient(registerRestTemplate = true)
public class PostServiceTest {

    static final String URL = "HTTP/MOCKURL";

    @Mock
    private RestTemplate template;

    @InjectMocks
    private PostService postService = new PostsServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(postService, "urlForUserPosts", URL);
    }

    @Test
    public void testGetAllPostsForAllUsers() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.OK));

        List<Post> results = postService.getAllPostsForAllUsers();
        assertThat(results.size(), Matchers.is(6));
        assertThat(results.get(0).getUserId(), Matchers.is(1L));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllPostsForAllUsersIOError() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenThrow(new RuntimeException());

        postService.getAllPostsForAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllPostsForAllUsersRespCodeErr() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.SERVICE_UNAVAILABLE));

        postService.getAllPostsForAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllPostsForAllUsersNullResp() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(null);

        postService.getAllPostsForAllUsers();
    }

    @Test
    public void testGetTop2UserPosts() throws IOException {

        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.OK));

        List<Post> results = postService.getUserPosts(1L, 2, 2);
        assertThat(results.size(), Matchers.is(2));
        assertThat(results.get(0).getUserId(), Matchers.is(1L));
        assertThat(results.get(0).getId(), Matchers.is(5L));
        assertThat(results.get(1).getUserId(), Matchers.is(1L));
        assertThat(results.get(1).getId(), Matchers.is(20L));
    }

    @Test
    public void testGetUserPosts() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.OK));

        List<Post> results = postService.getUserPosts(1L, -1, 3);
        assertThat(results.size(), Matchers.is(3));
        assertThat(results.get(0).getUserId(), Matchers.is(1L));
    }

    @Test
    public void testGetUserPostsForInvalidLimit() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.OK));

        List<Post> results = postService.getUserPosts(1L, -1, -3);
        assertThat(results.size(), Matchers.is(0));
    }

    @Test
    public void testGetEmptyUserPosts() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Post>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getPosts(), HttpStatus.OK));

        List<Post> results = postService.getUserPosts(3L, 2, 1);
        assertThat(results.size(), Matchers.is(0));
    }


}