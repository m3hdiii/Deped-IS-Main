package com.deped;

public class ResultBean {
    private String headTagTitle;
    private String headTagDescription;
    private String heading;
    private String successMessage;
    private String failureMessage;

    public ResultBean(String headTagTitle, String headTagDescription, String heading, String successMessage, String failureMessage) {
        this.headTagTitle = headTagTitle;
        this.headTagDescription = headTagDescription;
        this.heading = heading;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
    }

    public String getHeadTagTitle() {
        return headTagTitle;
    }

    public String getHeadTagDescription() {
        return headTagDescription;
    }

    public String getHeading() {
        return heading;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
