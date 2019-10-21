package com.kv.blog.service;

import com.kv.blog.BlogTestUtils;
import com.kv.blog.dto.User;
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
@RestClientTest({UsersServiceImpl.class})
@AutoConfigureWebClient(registerRestTemplate = true)
public class UsersServiceTest {

    static final String URL = "HTTP/MOCKURL";

    @Mock
    private RestTemplate template;

    @InjectMocks
    private UserService usersService = new UsersServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(usersService, "urlForUserDetails", URL);
    }

    @Test
    public void testGetAllUsers() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers(), HttpStatus.OK));

        List<User> results = usersService.getAllUsers();
        assertThat(results.size(), Matchers.is(10));
        assertThat(results.get(0).getId(), Matchers.is(1L));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllUsersIOError() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenThrow(new RuntimeException());

        usersService.getAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllUsersRespCodeErr() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers(), HttpStatus.SERVICE_UNAVAILABLE));

        usersService.getAllUsers();
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testGetAllUsersNullResp() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(null);

        usersService.getAllUsers();
    }

    @Test
    public void testGetUserById() throws IOException {
        long userID = 2l;
        Mockito
                .when(template.getForEntity(
                        URL + "/" + userID,
                        User.class))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers().stream()
                        .filter(u -> u.getId() == userID).findFirst().get(), HttpStatus.OK));
        User result = usersService.getUserByID(userID);
        assertThat(result.getId(), Matchers.is(userID));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testExceptionForGetUserById() throws IOException {
        long userID = 2l;
        Mockito
                .when(template.getForEntity(
                        URL + "/" + userID,
                        User.class))
                .thenReturn(null);
        User result = usersService.getUserByID(userID);
        assertThat(result.getId(), Matchers.is(userID));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testNulExceptionForGetUserById() throws IOException {
        long userID = 2l;
        Mockito
                .when(template.getForEntity(
                        URL + "/" + userID,
                        User.class))
                .thenReturn(null);
        User result = usersService.getUserByID(userID);
        assertThat(result.getId(), Matchers.is(userID));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testUserNotFoundForGetUserById() throws IOException {
        Mockito
                .when(template.getForEntity(
                        URL + "/" + 1,
                        User.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        usersService.getUserByID(1);
    }

    @Test
    public void testgetUsers() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers(), HttpStatus.OK));

        List<User> results = usersService.getUsers(1, 3);
        assertThat(results.size(), Matchers.is(3));
        assertThat(results.get(0).getId(), Matchers.is(2l));
    }

    @Test
    public void testGetUsersForInvalidLimit() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers(), HttpStatus.OK));

        List<User> results = usersService.getUsers(1l, -1);
        assertThat(results.size(), Matchers.is(0));
    }

    @Test
    public void testGetEmptyUserUsers() throws IOException {
        Mockito
                .when(template.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<User>>() {
                        }))
                .thenReturn(new ResponseEntity<>(BlogTestUtils.getUsers(), HttpStatus.OK));

        List<User> results = usersService.getUsers(30l, 2);
        assertThat(results.size(), Matchers.is(0));
    }

}