package com.kv.blog.errors;

public class BlogExceptionMessage {
    private String appErrorCode;
    private String appErrorMessage;
    private String appErrorDescription;
    private AppErrorType appErrorType = AppErrorType.ERROR;

    public BlogExceptionMessage() {
    }

    public BlogExceptionMessage(String appErrorCode, String appErrorMessage, String appErrorDescription, AppErrorType appErrorType) {
        this.appErrorCode = appErrorCode;
        this.appErrorMessage = appErrorMessage;
        this.appErrorDescription = appErrorDescription;
        this.appErrorType = appErrorType;
    }

    public String getAppErrorCode() {
        return appErrorCode;
    }

    public void setAppErrorCode(String appErrorCode) {
        this.appErrorCode = appErrorCode;
    }

    public String getAppErrorMessage() {
        return appErrorMessage;
    }

    public void setAppErrorMessage(String appErrorMessage) {
        this.appErrorMessage = appErrorMessage;
    }

    public String getAppErrorDescription() {
        return appErrorDescription;
    }

    public void setAppErrorDescription(String appErrorDescription) {
        this.appErrorDescription = appErrorDescription;
    }

    public AppErrorType getAppErrorType() {
        return appErrorType;
    }

    public void setAppErrorType(AppErrorType appErrorType) {
        this.appErrorType = appErrorType;
    }

    enum AppErrorType {ERROR, WARN}
}
