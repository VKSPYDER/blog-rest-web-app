package com.kv.blog.controller;

import com.kv.blog.BlogTestUtils;
import com.kv.blog.errors.ResourceUnavailableException;
import com.kv.blog.service.UsersServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UsersServiceImpl userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testHome() throws Exception {

        Mockito.when(userService.getUsers(0l, 1)).thenReturn(BlogTestUtils.getUsers());

        mockMvc.perform(get("/users").param("after", "0").param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }

    @Test
    public void testHome5XXStatus() throws Exception {
        Mockito.when(userService.getUsers(0l, 3)).thenThrow(new ResourceUnavailableException("Resource no availale"));
        mockMvc.perform(get("/users").param("after", "0").param("limit", "3"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("BLOG-EXTERNAL-SERVICE-UNAVAILABLE")))
                .andExpect(jsonPath("appErrorMessage", is("External Service unavailable")));
    }

    @Test
    public void testHome5XXInvalidReqError() throws Exception {
        mockMvc.perform(get("/users").param("after", "tttt").param("limit", "3"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("UNKOWN_ERROR")));
    }

    @Test
    public void testGetUserDetailById() throws Exception {

        Mockito.when(userService.getUserByID(1)).thenReturn(BlogTestUtils.getUsers().stream().filter(u -> u.getId() == 1).findFirst().get());

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Leanne Graham")));
    }

    @Test
    public void testGetUserDetailById5XXStatus() throws Exception {
        mockMvc.perform(get("/user/1-00"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("appErrorType", is("ERROR")))
                .andExpect(jsonPath("appErrorCode", is("UNKOWN_ERROR")));
    }
}