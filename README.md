
# User APi

The user Api is used to store user and update user data. Also utilized to store time to time user locations ,retrive latest user location and 
retrieve user recent location based on time.

## Authors

- [@kalaivanan014](https://github.com/kalaivanan014)


## API Reference

#### Save and Update User Data

```http
  POST /user/save
```


#### Save User Location 

```http
  POST /user/location
```

#### GET User Latest Location 

```http
  GET /user/location/latest/{userId}
  
```
#### GET User Recent Location 

```http
  GET /user/location/recent/{userId}



For further details of teh API kindly refer teh swagger,yaml from root directory 
```
## Run Locally

Clone the project


  git clone https://github.com/kalaivanan014/user-api.git


Go to the project directory

  cd user-api

Install dependencies and test 

  mvn clean install


Start the server

mvn spring-boot:run


