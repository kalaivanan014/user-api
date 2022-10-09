package com.data.user.controller;

import com.data.user.request.UserLocationRequest;
import com.data.user.request.UserRequest;
import com.data.user.response.UserLatestLocationResponse;
import com.data.user.response.UserRecentLocationsResponse;
import com.data.user.response.UserResponse;
import com.data.user.service.UserService;
import com.data.user.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController

public class UserController {

    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * method to create the user data
     *
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity createUser(@Valid @RequestBody UserRequest createRequest) {
        logger.info("Inside Create User Method");
        UserResponse response = userService.saveUserData(createRequest);
        logger.info("Exiting Create User Method");
        return ResponseEntity.ok(response);
    }

    /**
     * @return
     */
    @PostMapping("/location")
    public ResponseEntity saveCurrentLocation(@Valid @RequestBody UserLocationRequest userLocationRequest) throws Exception {
        logger.info("Entering saveCurrentLocation Method");
        try {
            userService.saveUserLocation(userLocationRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        logger.info("Exiting saveCurrentLocation Method");
        return new ResponseEntity<>(Constants.LOCATION_SAVED, HttpStatus.OK);
    }

    /**
     * @return
     */
    @GetMapping("/location/latest/{userId}")
    public ResponseEntity getLatestLocation(@PathVariable String userId) throws Exception {
        logger.info("Entering getLatestLocation Method");

        UserLatestLocationResponse response = null;
        try {
            response = userService.getUserLatestLocation(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        logger.info("Exiting getLatestLocation Method");

        return ResponseEntity.ok(response);
    }

    /**
     * returns recent 30 location of the user
     *
     * @return
     */
    @GetMapping("/location/recent/{userId}")
    public ResponseEntity getRecentLocations(@PathVariable String userId) throws Exception {
        logger.info("Entering getRecentLocations Method");

        UserRecentLocationsResponse response = null;
        try {
            response = userService.getRecentLocations(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        logger.info("Exiting getRecentLocations Method");

        return ResponseEntity.ok(response);
    }
}
