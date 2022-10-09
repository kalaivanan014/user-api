package com.data.user.service;

import com.data.user.request.UserLocationRequest;
import com.data.user.request.UserRequest;
import com.data.user.response.UserLatestLocationResponse;
import com.data.user.response.UserRecentLocationsResponse;
import com.data.user.response.UserResponse;


public interface UserService {

    public UserResponse saveUserData(UserRequest createRequest);
    public void saveUserLocation(UserLocationRequest userLocationRequest) throws Exception;
    UserLatestLocationResponse getUserLatestLocation(String userId) throws Exception;
    UserRecentLocationsResponse getRecentLocations(String userId) throws Exception;

}
