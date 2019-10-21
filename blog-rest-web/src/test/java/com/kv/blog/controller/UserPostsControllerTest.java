package com.kv.blog.controller;

import com.kv.blog.BlogTestUtils;
import com.kv.blog.errors.ResourceUnavailableException;
import com.kv.blog.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserPostsController.class)
public class UserPostsControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetUserPosts() throws Exception {

        Mockito.when(postService.getUserPosts(1l, 4, 3)).thenReturn(BlogTestUtils.getPosts().stream().filter(c -> c.getUserId() == 1 && c.getId() > 4).collect(Collectors.toList()));
        mockMvc.perform(get("/posts").param("userId", "1").param("after", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userId", is(1)));
    }

    @Test
    public void testGetUserPostsFor5XXStatus() throws Exception {
        Mockito.when(postService.getUserPosts(0l, 0, 3)).thenThrow(new ResourceUnavailableException("Resource no availale"));
        mockMvc.perform(get("/posts").param("userId", "0"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("BLOG-EXTERNAL-SERVICE-UNAVAILABLE")))
                .andExpect(jsonPath("appErrorMessage", is("External Service unavailable")));
    }

    @Test
    public void testGetUserPostsFor5XXInvalidReqError() throws Exception {
        mockMvc.perform(get("/posts").param("userId", "tttt"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("UNKOWN_ERROR")));
    }

    @Test
    public void testGetUserPosts4XXStatus() throws Exception {
        mockMvc.perform(get("/posts-all"))
                .andExpect(status().is4xxClientError());
    }
}