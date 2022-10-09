package com.data.user.util;

import com.data.user.util.Location;

import java.sql.Timestamp;

public class Locations {
    private Timestamp createdOn;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}
