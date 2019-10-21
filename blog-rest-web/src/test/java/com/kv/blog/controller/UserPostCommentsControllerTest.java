package com.kv.blog.controller;


import com.kv.blog.BlogTestUtils;
import com.kv.blog.errors.ResourceUnavailableException;
import com.kv.blog.service.CommentsService;
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
@WebMvcTest(UserPostCommentsController.class)
public class UserPostCommentsControllerTest {

    @MockBean
    CommentsService commentsService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetUserCommentsForPost() throws Exception {

        Mockito.when(commentsService.getCommentsForThePost(1l)).thenReturn(BlogTestUtils.getComments().stream().filter(c -> c.getPostId() == 1).collect(Collectors.toList()));
        mockMvc.perform(get("/comments").param("postId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].postId", is(1)))
                .andExpect(jsonPath("$[1].postId", is(1)));
    }

    @Test
    public void testGetUserCommentsForPost5XXStatus() throws Exception {
        Mockito.when(commentsService.getCommentsForThePost(0l)).thenThrow(new ResourceUnavailableException("Resource no availale"));
        mockMvc.perform(get("/comments").param("postId", "0"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("BLOG-EXTERNAL-SERVICE-UNAVAILABLE")))
                .andExpect(jsonPath("appErrorMessage", is("External Service unavailable")));
    }

    @Test
    public void testGetUserCommentsForPost5XXInvalidReqError() throws Exception {
        mockMvc.perform(get("/comments").param("postId", "tttt"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("UNKOWN_ERROR")));
    }

    @Test
    public void testHome() throws Exception {
        Mockito.when(commentsService.getAllCommentsFromAllUsers()).thenReturn(BlogTestUtils.getComments());
        mockMvc.perform(get("/all-comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testGetUserDetailById4XXStatus() throws Exception {
        mockMvc.perform(get("/allcomments"))
                .andExpect(status().is4xxClientError());
    }
}