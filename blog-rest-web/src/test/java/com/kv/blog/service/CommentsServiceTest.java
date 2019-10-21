package com.kv.blog.service;

import com.kv.blog.BlogTestUtils;
import com.kv.blog.dto.Comment;
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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@RestClientTest({CommentsServiceImpl.class})
@AutoConfigureWebClient(registerRestTemplate = true)
public class CommentsServiceTest {

    static final String URL = "HTTP/MOCKURL";

    @Mock
    private RestTemplate template;

    @InjectMocks
    private CommentsService commentsService = new CommentsServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(commentsService, "urlForUserCommentsOnPosts", URL);
    }

    @Test
    public void testGetAllComments() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getComments(), HttpStatus.OK));

        List<Comment> results = commentsService.getAllCommentsFromAllUsers();
        assertThat(results.size(), Matchers.is(5));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllCommentsIOError() {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenThrow(new RuntimeException());
        commentsService.getAllCommentsFromAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllCommentsRespCodeErr() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getComments(), HttpStatus.SERVICE_UNAVAILABLE));

        commentsService.getAllCommentsFromAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllCommentsNullResp() {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(null);

        commentsService.getAllCommentsFromAllUsers();
    }

    @Test
    public void testGetCommentById() throws IOException {
        long postId = 2l;
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getComments(), HttpStatus.OK));
        List<Comment> result = commentsService.getCommentsForThePost(postId);
        assertThat(result.get(0).getPostId(), Matchers.is(postId));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testExceptionForGetCommentById() throws IOException {
        long postId = 2l;
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getComments(), HttpStatus.GATEWAY_TIMEOUT));
        List<Comment> result = commentsService.getCommentsForThePost(postId);
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testCommentNotFoundForGetCommentById() {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        commentsService.getCommentsForThePost(1l);
    }

    @Test
    public void testGetEmptyCommentComments() {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {
                        }))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));

        List<Comment> results = commentsService.getCommentsForThePost(1l);
        assertThat(results.size(), Matchers.is(0));
    }

}