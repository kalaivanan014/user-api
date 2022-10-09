package com.data.user.request;

import com.data.user.util.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class UserLocationRequest {
    @NotBlank(message = "Please provide userId")
    private String  userId;
    private Timestamp createdOn;
    @NotNull(message = "Provide Location Data")
    private Location location;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
