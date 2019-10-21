package com.kv.blog.service;

import com.kv.blog.dto.User;
import com.kv.blog.errors.InvalidUserRequestException;
import com.kv.blog.errors.RecordNotFoundException;
import com.kv.blog.errors.ResourceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kv.blog.utils.BlogResponseUtils.handleResponseCodes;

@Service
public class UsersServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${service.get.users.url}")
    private String urlForUserDetails;

    @Autowired
    private RestTemplate template;

    @Override
    public List<User> getAllUsers() {
        log.info("Returning all the users from the usersApi :{}", urlForUserDetails);
        try {
            ResponseEntity<List<User>> response = template.exchange(
                    urlForUserDetails,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<User>>() {
                    });
            handleResponseCodes(response);
            return response.getBody();
        } catch (Exception e) {
            throw new ResourceUnavailableException(e);
        }
    }

    @Override
    public List<User> getUsers(long after, int limit) {
        log.info("Returning {} users after user ID:{}, from the usersApi :{}", limit, after, urlForUserDetails);
        try {
            return getAllUsers().stream().sorted(Comparator.comparing(User::getId)).filter(u -> after <= 0 || u.getId() > after)
                    .limit(limit < 0 ? 0 : limit).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("", e);
            throw new ResourceUnavailableException(e);
        }
    }

    @Override
    public User getUserByID(long id) {
        log.info("Accessing All the users from URL : {}/{}", urlForUserDetails, id);
        if (id <= 0) {
            throw new InvalidUserRequestException();
        }
        try {

            String url = new StringBuilder(urlForUserDetails).append("/").append(id).toString();
            ResponseEntity<User> response = template.getForEntity(url, User.class);
            handleResponseCodes(response);
            log.info("list - {}", response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RecordNotFoundException(ex);
        } catch (Exception e) {
            throw new ResourceUnavailableException(e);
        }

    }
}
