package com.kv.blog.controller;

import com.kv.blog.dto.User;
import com.kv.blog.service.UsersServiceImpl;
import com.kv.blog.utils.BlogResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UsersServiceImpl usersService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> home(@RequestParam(value = "after", defaultValue = "0") long after, @RequestParam(value = "limit", defaultValue = "3") int limit) {
        log.info("Accessing {} users the Users after userID {} ", limit, after);
        return usersService.getUsers(after, limit).stream().
                map(BlogResponseUtils::updateUserFirstName).collect(Collectors.toList());
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserDetailById(@PathVariable(value = "id") long id) {
        log.info("Accessing the User details for {}", id);
        return usersService.getUserByID(id);
    }
}
