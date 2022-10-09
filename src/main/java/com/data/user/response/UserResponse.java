package com.data.user.response;

import java.sql.Timestamp;
public class UserResponse {
    private String userId;
    private Timestamp currentTimeStamp;
    private String emailId;
    private String firstName;
    private String secondName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return secondName;
    }

    public void setLastName(String lastName) {
        this.secondName = lastName;
    }
}
