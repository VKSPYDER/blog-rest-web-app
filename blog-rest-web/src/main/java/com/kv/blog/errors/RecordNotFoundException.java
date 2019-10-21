package com.kv.blog.errors;

/**
 * Exception Message wrapper for record not found error.
 */
public class RecordNotFoundException extends AbstractBlogException {

    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(Exception e) {
        super(e);
    }

    @Override
    BlogExceptionMessage defaultBlogExceptionMessage() {
        return new BlogExceptionMessage("BLOG-GENERAL-ERR", "Record Not Found",
                "Oops something went wrong with App... Please try later.",
                BlogExceptionMessage.AppErrorType.ERROR);
    }
}
