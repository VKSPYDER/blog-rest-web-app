package com.kv.blog.errors;

/**
 * Exception for Invalid user request.
 */
public class InvalidUserRequestException extends AbstractBlogException {
    public InvalidUserRequestException() {
        super();
    }

    public InvalidUserRequestException(String message) {
        super(message);
    }

    public InvalidUserRequestException(Exception e) {
        super(e);
    }

    @Override
    BlogExceptionMessage defaultBlogExceptionMessage() {
        return new BlogExceptionMessage("BLOG-INVALID-USER-REQ", "Invalid User Request", "Request is invalid, please check you input", BlogExceptionMessage.AppErrorType.ERROR);
    }
}
