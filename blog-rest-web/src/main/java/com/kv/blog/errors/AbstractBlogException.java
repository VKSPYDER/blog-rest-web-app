package com.kv.blog.errors;

/**
 * Abstract Blog Exception/Error Message.
 */
public abstract class AbstractBlogException extends RuntimeException {

    private BlogExceptionMessage blogExceptionMessage;

    /**
     * default constructor.
     */
    public AbstractBlogException() {
        super();
        setBlogExceptionMessage(defaultBlogExceptionMessage());
    }

    public AbstractBlogException(BlogExceptionMessage blogExceptionMessage) {
        super();
        this.blogExceptionMessage = blogExceptionMessage;
    }

    public AbstractBlogException(String message) {
        super(message);
        setBlogExceptionMessage(defaultBlogExceptionMessage());
    }

    public AbstractBlogException(Throwable e) {
        super(e);
        setBlogExceptionMessage(defaultBlogExceptionMessage());
    }

    /**
     * Prepare Default Blog error Message.
     * @return
     */
    abstract BlogExceptionMessage defaultBlogExceptionMessage();

    public BlogExceptionMessage getBlogExceptionMessage() {
        return blogExceptionMessage;
    }

    public void setBlogExceptionMessage(BlogExceptionMessage blogExceptionMessage) {
        this.blogExceptionMessage = blogExceptionMessage;
    }
}
