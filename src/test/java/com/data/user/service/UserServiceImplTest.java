package com.data.user.service;

import com.data.user.Repository.UserLocationRepository;
import com.data.user.Repository.UserRepository;
import com.data.user.entity.UserEntity;
import com.data.user.entity.UserLocationEntity;
import com.data.user.request.UserLocationRequest;
import com.data.user.request.UserRequest;
import com.data.user.response.UserLatestLocationResponse;
import com.data.user.response.UserRecentLocationsResponse;
import com.data.user.response.UserResponse;
import com.data.user.util.Constants;
import com.data.user.util.Location;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepo;

    @Mock
    private UserLocationRepository userLocationRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testSaveUserData(){
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        UserRequest req= new UserRequest();
        req.setEmailId("user@gmail.com");
        req.setFirstName("firstName");
        req.setLastName("lastName");
        Mockito.when(userRepo.findByemailId(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(entity);
        UserResponse asd = userService.saveUserData(req);
        Assert.hasText(asd.getEmailId(), req.getEmailId());
    }

    @Test
    public void errorScenarioSaveUserData(){
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(UUID.randomUUID().toString());
        Mockito.when(userRepo.findByemailId(Mockito.anyString())).thenReturn(Optional.of(entity));
        UserRequest req= new UserRequest();
        req.setEmailId("user@gmail.com");
        req.setFirstName("firstNameDiff");
        req.setLastName("lastName");
        entity.setFirstName("firstNameDiff");
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(entity);
        UserResponse asd = userService.saveUserData(req);
        Assert.hasText(asd.getEmailId(), req.getEmailId());
        Assert.hasText(asd.getFirstName(), req.getFirstName());
    }

    @Test
    void testSaveUserLocation_UserIdNotExist(){
        UserLocationRequest userLocationRequest= new UserLocationRequest();
        userLocationRequest.setUserId(UUID.randomUUID().toString());
        userLocationRequest.setCreatedOn(Timestamp.from(Instant.now()));
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        userLocationRequest.setLocation(location);
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());

        try {
            userService.saveUserLocation(userLocationRequest);
        }catch(Exception e){
            Assert.hasText(e.getMessage(), Constants.USER_NOT_FOUND);
        }
    }

    @Test
    void testSaveUserLocation_Success() throws Exception {
         final String uuid=UUID.randomUUID().toString();
        UserLocationRequest userLocationRequest= new UserLocationRequest();
        userLocationRequest.setUserId(uuid);
        userLocationRequest.setCreatedOn(Timestamp.from(Instant.now()));
        Location location = new Location();
        location.setLatitude("103.13123213");
        location.setLongitude("204.99707907");
        userLocationRequest.setLocation(location);
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
        userService.saveUserLocation(userLocationRequest);

    }

    @Test
    void getLatestLocation_Success() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        UserLocationEntity locationEntity= new UserLocationEntity();
        locationEntity.setUserId(uuid);
        locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
        locationEntity.setLatitudes("103.13123213");
        locationEntity.setLongitude("204.99707907");
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(userLocationRepository.findTop1ByUserIdOrderByCreatedOnDesc(uuid)).thenReturn(Optional.of(locationEntity));
        UserLatestLocationResponse res = userService.getUserLatestLocation(uuid);
        Assert.hasText(res.getEmailId(),"user@gmail.com");

    }


    @Test
    void getLatestLocation_UserNotFound() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        UserLocationEntity locationEntity= new UserLocationEntity();
        locationEntity.setUserId(uuid);
        locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
        locationEntity.setLatitudes("103.13123213");
        locationEntity.setLongitude("204.99707907");
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.empty());

       try {
           UserLatestLocationResponse res = userService.getUserLatestLocation(uuid);
       }catch(Exception e){
           Assert.hasText(e.getMessage(),Constants.USER_NOT_FOUND);
       }

    }

    @Test
    void getLatestLocation_LocationNotExist() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        UserLocationEntity locationEntity= new UserLocationEntity();
        locationEntity.setUserId(uuid);
        locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
        locationEntity.setLatitudes("103.13123213");
        locationEntity.setLongitude("204.99707907");
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(userLocationRepository.findTop1ByUserIdOrderByCreatedOnDesc(uuid)).thenReturn(Optional.empty());
        try {
            UserLatestLocationResponse res = userService.getUserLatestLocation(uuid);
        }catch(Exception e){
            Assert.hasText(e.getMessage(),Constants.LOCATIONS_NOT_AVAILABLE);
        }

    }
    
    @Test
    void getRecentLocations_Success() throws Exception {
        final String uuid=UUID.randomUUID().toString();
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        UserLocationEntity locationEntity= new UserLocationEntity();
        locationEntity.setUserId(uuid);
        locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
        locationEntity.setLatitudes("103.13123213");
        locationEntity.setLongitude("204.99707907");
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
        Mockito.when(userLocationRepository.findTop20ByUserIdOrderByCreatedOnDesc(uuid)).thenReturn(Optional.of(List.of(locationEntity)));
        UserRecentLocationsResponse res = userService.getRecentLocations(uuid);
        Assert.notEmpty(res.getLocations());
        
    }


    @Test
    void getRecentLocations_UserIdNotExist() throws Exception {
        final String uuid = UUID.randomUUID().toString();
        UserEntity entity = new UserEntity();
        entity.setEmailId("user@gmail.com");
        entity.setFirstName("firstName");
        entity.setSecondName("lastName");
        entity.setId(uuid);
        UserLocationEntity locationEntity = new UserLocationEntity();
        locationEntity.setUserId(uuid);
        locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
        locationEntity.setLatitudes("103.13123213");
        locationEntity.setLongitude("204.99707907");
        Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
        try {
            UserRecentLocationsResponse res = userService.getRecentLocations(uuid);
        } catch (Exception e) {
            Assert.hasText(e.getMessage(), Constants.USER_NOT_FOUND);

        }
    }

        @Test
        void getRecentLocations_Location_NotExist() throws Exception {
            final String uuid=UUID.randomUUID().toString();
            UserEntity entity = new UserEntity();
            entity.setEmailId("user@gmail.com");
            entity.setFirstName("firstName");
            entity.setSecondName("lastName");
            entity.setId(uuid);
            UserLocationEntity locationEntity= new UserLocationEntity();
            locationEntity.setUserId(uuid);
            locationEntity.setCreatedOn(Timestamp.from(Instant.now()));
            locationEntity.setLatitudes("103.13123213");
            locationEntity.setLongitude("204.99707907");
            Mockito.when(userRepo.findById(uuid)).thenReturn(Optional.of(entity));
            Mockito.when(userLocationRepository.findTop20ByUserIdOrderByCreatedOnDesc(uuid)).thenReturn(Optional.empty());

            try {
                UserRecentLocationsResponse res = userService.getRecentLocations(uuid);
            }
            catch (Exception e){
                Assert.hasText(e.getMessage(),Constants.LOCATIONS_NOT_AVAILABLE);

            }

    }
}