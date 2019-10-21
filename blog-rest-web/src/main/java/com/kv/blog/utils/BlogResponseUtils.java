package com.kv.blog.utils;

import com.kv.blog.dto.User;
import com.kv.blog.errors.RecordNotFoundException;
import com.kv.blog.errors.ResourceUnavailableException;
import org.springframework.http.ResponseEntity;

public class BlogResponseUtils {
    public static void handleResponseCodes(ResponseEntity<?> responseEntity) {
        if (responseEntity == null || responseEntity.getBody() == null) {
            throw new ResourceUnavailableException("Invalid response received !!!, please try again later.");
        } else if (responseEntity.getStatusCode().is5xxServerError()) {
            throw new ResourceUnavailableException(responseEntity.toString());
        } else if (responseEntity.getStatusCode().is4xxClientError()) {
            throw new RecordNotFoundException(responseEntity.toString());
        }
    }

    public static User updateUserFirstName(User user) {
        if (user != null && user.getName() != null) {
            user.setName(user.getName().split(" ")[user.getName().indexOf('.') <= 0 ? 0 : 1]);
        }
        return user;
    }

}
