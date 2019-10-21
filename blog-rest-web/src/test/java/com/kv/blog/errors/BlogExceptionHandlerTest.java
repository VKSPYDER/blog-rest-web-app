package com.kv.blog.errors;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThat;

/*
 * Junit Test case for @BlogExceptionHandler
 */
public class BlogExceptionHandlerTest {

    private BlogExceptionHandler handler;

    @Before
    public void setUp() {
        handler = new BlogExceptionHandler();
    }

    @Test
    public void testRecordNotFoundException() {
        ResponseEntity<BlogExceptionMessage> errorEntity = handler.recordNotFoundException(new RecordNotFoundException());
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-GENERAL-ERR"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Record Not Found"));

        errorEntity = handler.recordNotFoundException(null);
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.NOT_FOUND));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-GENERAL-ERR"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Record Not Found"));
    }

    @Test
    public void testResourceUnavailableException() {
        ResponseEntity<BlogExceptionMessage> errorEntity = handler.resourceUnavailableException(new ResourceUnavailableException());
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-EXTERNAL-SERVICE-UNAVAILABLE"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("External Service unavailable"));

        errorEntity = handler.resourceUnavailableException(null);
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-EXTERNAL-SERVICE-UNAVAILABLE"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("External Service unavailable"));
    }

    @Test
    public void testInvalidUserRequestException() {
        ResponseEntity<BlogExceptionMessage> errorEntity = handler.invalidUserRequestException(new InvalidUserRequestException());
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.BAD_REQUEST));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-INVALID-USER-REQ"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Invalid User Request"));

        errorEntity = handler.invalidUserRequestException(null);
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.BAD_REQUEST));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("BLOG-INVALID-USER-REQ"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Invalid User Request"));
    }

    @Test
    public void testGlobleExcpetionHandler() {
        ResponseEntity<BlogExceptionMessage> errorEntity = handler.globleExcpetionHandler(new Exception("Server Error"));
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("UNKOWN_ERROR"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Server Error"));

        errorEntity = handler.globleExcpetionHandler(null);
        assertThat(errorEntity.getStatusCode(), Matchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorEntity.getBody(), Matchers.any(BlogExceptionMessage.class));
        assertThat(errorEntity.getBody().getAppErrorCode(), Matchers.is("UNKOWN_ERROR"));
        assertThat(errorEntity.getBody().getAppErrorMessage(), Matchers.is("Unknown error occured"));
    }

}