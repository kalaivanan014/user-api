package com.data.user.controller;

import com.data.user.Repository.UserRepository;
import com.data.user.request.UserLocationRequest;
import com.data.user.request.UserRequest;
import com.data.user.response.UserLatestLocationResponse;
import com.data.user.response.UserRecentLocationsResponse;
import com.data.user.response.UserResponse;
import com.data.user.service.UserService;
import com.data.user.util.Location;
import com.data.user.util.Locations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createUserSuccess_Test() throws Exception {
        UserRequest request = new UserRequest();
        request.setEmailId("user@gmail.com");
        request.setFirstName("firstname");
        request.setLastName("lastename");
        Mockito.when(userService.saveUserData(Mockito.any())).thenReturn(getUserResponse());
        mockMvc.perform(post("/save").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(request))).
                andExpect(status().isOk());
    }


    @Test
    void saveCurrentLocation_test() throws Exception {
        UserLocationRequest userLocationRequest= new UserLocationRequest();
        userLocationRequest.setUserId(UUID.randomUUID().toString());
        userLocationRequest.setCreatedOn(Timestamp.from(Instant.now()));
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        userLocationRequest.setLocation(location);
        Mockito.doNothing().when(userService).saveUserLocation(Mockito.any());
        mockMvc.perform(post("/location").contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(userLocationRequest))).
                andExpect(status().isOk());
    }


    @Test
    void saveCurrentLocation_Error() throws Exception {
        UserLocationRequest userLocationRequest= new UserLocationRequest();
        userLocationRequest.setUserId(UUID.randomUUID().toString());
        userLocationRequest.setCreatedOn(Timestamp.from(Instant.now()));
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        userLocationRequest.setLocation(location);
        Mockito.doThrow(new Exception()).when(userService).saveUserLocation(Mockito.any());
        mockMvc.perform(post("/location").contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(userLocationRequest))).
                andExpect(status().isNotFound());
    }

    @Test
    void getLatestLocation_test() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserLatestLocationResponse userLatestLocationResponse = new UserLatestLocationResponse();
        userLatestLocationResponse.setUserId(uuid);
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        userLatestLocationResponse.setLocation(location);
        userLatestLocationResponse.setEmailId("user@gmail.com");
        userLatestLocationResponse.setFirstName("firstname");
        userLatestLocationResponse.setLastName("lastename");
        userLatestLocationResponse.setCreatedOn(Timestamp.from(Instant.now()));
        Mockito.when(userService.getUserLatestLocation(uuid)).thenReturn(userLatestLocationResponse);
        mockMvc.perform(get("/location/latest/{userId}",uuid)).
                andExpect(status().isOk());
    }

    @Test
    void getLatestLocationError() throws Exception {
        final String uuid=UUID.randomUUID().toString();

        Mockito.when(userService.getUserLatestLocation(uuid)).thenThrow(new Exception());
        mockMvc.perform(get("/location/latest/{userId}",uuid)).
                andExpect(status().isNotFound());

    }


    @Test
    void getRecentLocations_test() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserRecentLocationsResponse userRecentLocationsResponse = new UserRecentLocationsResponse();
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        Locations locations = new Locations();
        locations.setLocation(location);
        locations.setCreatedOn(Timestamp.from(Instant.now()));
        userRecentLocationsResponse.setLocations(List.of(locations));
        Mockito.when(userService.getRecentLocations(uuid)).thenReturn(userRecentLocationsResponse);
        mockMvc.perform(get("/location/recent/{userId}",uuid)).
                andExpect(status().isOk());
    }

    @Test
    void recentLocation_Error() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        Mockito.when(userService.getRecentLocations(uuid)).thenThrow(new Exception());
        mockMvc.perform(get("/location/recent/{userId}",uuid)).
                andExpect(status().isNotFound());
    }


    private UserResponse getUserResponse(){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(UUID.randomUUID().toString());
        userResponse.setEmailId("user@gmail.com");
        userResponse.setFirstName("firstname");
        userResponse.setLastName("lastName");
        userResponse.setCurrentTimeStamp(Timestamp.from(Instant.now()));
        return userResponse;
    }

}