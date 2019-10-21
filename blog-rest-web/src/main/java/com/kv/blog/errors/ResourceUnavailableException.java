package com.kv.blog.errors;

/**
 * Exception Message with Custom message entity for Unavailable resource.
 */
public class ResourceUnavailableException extends AbstractBlogException {
    public ResourceUnavailableException() {
        super();
    }

    public ResourceUnavailableException(String message) {
        super(message);
    }

    public ResourceUnavailableException(Throwable e) {
        super(e);
    }

    public BlogExceptionMessage defaultBlogExceptionMessage() {
        return new BlogExceptionMessage("BLOG-EXTERNAL-SERVICE-UNAVAILABLE", "External Service unavailable",
                "External Service unavailable, Please try later.", BlogExceptionMessage.AppErrorType.ERROR);
    }
}
