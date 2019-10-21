package com.kv.blog.utils;

import com.kv.blog.dto.User;
import com.kv.blog.errors.RecordNotFoundException;
import com.kv.blog.errors.ResourceUnavailableException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BlogResponseUtilsTest {

    @Test(expected = ResourceUnavailableException.class)
    public void testHandleResponseCodesForNullResp() {
        BlogResponseUtils.handleResponseCodes(null);
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testHandleResponseCodesForEmptyBody() {
        BlogResponseUtils.handleResponseCodes(new ResponseEntity<>(HttpStatus.MULTI_STATUS));
    }

    @Test(expected = ResourceUnavailableException.class)
    public void testHandleResponseCodesFor5xxErrorCode() {
        BlogResponseUtils.handleResponseCodes(new ResponseEntity<>(new User(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test(expected = RecordNotFoundException.class)
    public void testHandleResponseCodesFor4xxErrorCode() {
        BlogResponseUtils.handleResponseCodes(new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetFirstName() {
        User user = new User();
        int originalHash = user.hashCode();
        assertThat(BlogResponseUtils.updateUserFirstName(user).hashCode(), Matchers.is(originalHash));

        String name = "";

        user.setName(name);

        originalHash = user.hashCode();

        assertThat(BlogResponseUtils.updateUserFirstName(user).hashCode(), Matchers.is(originalHash));

        assertThat(BlogResponseUtils.updateUserFirstName(user).getName(), Matchers.is(name));


        name = "first name";

        user.setName(name);

        originalHash = user.hashCode();

        assertTrue(BlogResponseUtils.updateUserFirstName(user).hashCode() != originalHash);

        assertThat(BlogResponseUtils.updateUserFirstName(user).getName(), Matchers.is(name.split(" ")[0]));


        name = "first. name";

        user.setName(name);

        originalHash = user.hashCode();

        assertTrue(BlogResponseUtils.updateUserFirstName(user).hashCode() != originalHash);

        assertThat(BlogResponseUtils.updateUserFirstName(user).getName(), Matchers.is(name.split(" ")[1]));


        name = "Mr. mister first name";

        user.setName(name);

        originalHash = user.hashCode();

        assertTrue(BlogResponseUtils.updateUserFirstName(user).hashCode() != originalHash);

        assertThat(BlogResponseUtils.updateUserFirstName(user).getName(), Matchers.is(name.split(" ")[1]));
    }
}
