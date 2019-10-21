package com.kv.blog.service;

import com.kv.blog.dto.User;

import java.util.List;

/**
 * Interface to access Blog User details.
 */
public interface UserService {
    /**
     * Get all the users registered in the Blog App.
     *
     * @return List<User>
     */
    List<User> getAllUsers();

    /**
     * Get all the users registered in the Blog App.
     *
     * @return List<User>
     */
    List<User> getUsers(long after, int limit);


    /**
     * Get the users registered in the Blog App by User ID.
     *
     * @param id - user ID
     * @return User
     */
    User getUserByID(long id);
}
