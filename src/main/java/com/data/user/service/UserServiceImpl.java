package com.data.user.service;

import com.data.user.Repository.UserLocationRepository;
import com.data.user.Repository.UserRepository;
import com.data.user.controller.UserController;
import com.data.user.entity.UserLocationEntity;
import com.data.user.util.Constants;
import com.data.user.util.Location;
import com.data.user.request.UserLocationRequest;
import com.data.user.request.UserRequest;
import com.data.user.entity.UserEntity;
import com.data.user.util.Locations;
import com.data.user.response.UserLatestLocationResponse;
import com.data.user.response.UserRecentLocationsResponse;
import com.data.user.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLocationRepository userLocationRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse saveUserData(UserRequest createRequest) {
        logger.info("Inside UserServiceImpl Method");
    UserResponse responseData= new UserResponse();
        UserEntity entity= new UserEntity();
        Optional<UserEntity> userData=userRepository.findByemailId(createRequest.getEmailId());
        if(userData.isPresent()){
            entity.setId(userData.get().getId());
        }else{
            entity.setId(UUID.randomUUID().toString());
        }
        entity.setCreatedOn(Timestamp.from(Instant.now()));
        entity.setEmailId(createRequest.getEmailId());
        entity.setFirstName(createRequest.getFirstName());
        entity.setSecondName(createRequest.getLastName());
        UserEntity result =userRepository.save(entity);
        responseData.setEmailId(result.getEmailId());
        responseData.setFirstName(result.getFirstName());
        responseData.setLastName(result.getSecondName());
        responseData.setCurrentTimeStamp(result.getCreatedOn());
        responseData.setUserId(result.getId());
        logger.info("Exiting UserServiceImpl method");
        return responseData;
    }

    @Override
    public void saveUserLocation(UserLocationRequest userLocationRequest) throws Exception {
        logger.info("Entering saveUserLocation Method");
        Optional<UserEntity> userData=userRepository.findById(userLocationRequest.getUserId());
        if(userData.isPresent()){
            UserLocationEntity locationEntity = new UserLocationEntity();
            locationEntity.setUserId(userLocationRequest.getUserId());
            locationEntity.setLongitude(userLocationRequest.getLocation().getLongitude());
            locationEntity.setLatitudes(userLocationRequest.getLocation().getLatitude());
            locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
            userLocationRepository.save(locationEntity);
        }else{
            throw new Exception(Constants.USER_NOT_FOUND);
    }
        logger.info("Exiting saveUserLocation Method");
    }
        public UserLatestLocationResponse getUserLatestLocation(String userId) throws Exception {
        logger.info("Entering getUserLatestLocation Method");
            UserLatestLocationResponse userLatestLocationResponse = null;
            Optional<UserEntity> userData=userRepository.findById(userId);
            if(userData.isPresent()) {
                //setting user related datas
                UserEntity userDataValue = userData.get();
                userLatestLocationResponse=new UserLatestLocationResponse();
                userLatestLocationResponse.setUserId(userId);
                userLatestLocationResponse.setFirstName(userDataValue.getFirstName());
                userLatestLocationResponse.setLastName(userDataValue.getSecondName());
                userLatestLocationResponse.setEmailId(userDataValue.getEmailId());
                //getting location values for user and setting the valeus in response
                Optional<UserLocationEntity> latestLocationData= userLocationRepository.findTop1ByUserIdOrderByCreatedOnDesc(userId);
                if(latestLocationData.isPresent()){
                    UserLocationEntity userLocationValue= latestLocationData.get();
                    Location locationVal=new Location();
                    locationVal.setLongitude(userLocationValue.getLongitude());
                    locationVal.setLatitude(userLocationValue.getLatitudes());
                    userLatestLocationResponse.setLocation(locationVal);
                    userLatestLocationResponse.setCreatedOn(userLocationValue.getCreatedOn());
                }else{
                    throw new Exception(Constants.LOCATIONS_NOT_AVAILABLE);
                }
            }else{
                throw new Exception(Constants.USER_NOT_FOUND);
            }
            logger.info("Exiting getUserLatestLocation Method");
           return userLatestLocationResponse;
        }

    @Override
    public UserRecentLocationsResponse getRecentLocations(String userId) throws Exception {
        logger.info("Entering getRecentLocations Method");
        UserRecentLocationsResponse userRecentLocationsResponse= null;
        Optional<UserEntity> userData=userRepository.findById(userId);
        if(userData.isPresent()) {
            Optional<List<UserLocationEntity>> recentLocationData = userLocationRepository.findTop20ByUserIdOrderByCreatedOnDesc(userId);
            if(recentLocationData.isPresent()){
                userRecentLocationsResponse= new UserRecentLocationsResponse();
                List<UserLocationEntity> recentLocationDataValue= recentLocationData.get();
                userRecentLocationsResponse.setUserId(userId);
                List<Locations> locationsList=new ArrayList<>();
                recentLocationDataValue.forEach(e->{
                    Locations locationsValue= new Locations();
                    locationsValue.setCreatedOn(e.getCreatedOn());
                    Location locationValue=new Location();
                    locationValue.setLatitude(e.getLatitudes());
                    locationValue.setLongitude(e.getLongitude());
                    locationsValue.setLocation(locationValue);
                    locationsList.add(locationsValue);
                });
                userRecentLocationsResponse.setLocations(locationsList);
            }else{
                throw new Exception(Constants.LOCATIONS_NOT_AVAILABLE);
            }
        }else{
            throw new Exception(Constants.USER_NOT_FOUND);
        }
        logger.info("Exiting getRecentLocations Method");
        return userRecentLocationsResponse;
    }


}
