package com.data.user.response;

import com.data.user.util.Locations;

import java.util.List;

public class UserRecentLocationsResponse {
    private String userId;
    List<Locations> locations;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }
}
