package pl.majkowski.DemoRestApi.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majkowski.DemoRestApi.dto.ApiResponse;
import pl.majkowski.DemoRestApi.dto.UserAgeDTO;
import pl.majkowski.DemoRestApi.dto.UserDto;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserBadRequestException;
import pl.majkowski.DemoRestApi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserApi {
    private static final Logger logger = LogManager.getLogger(UserApi.class);

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/loadFile")
    public ResponseEntity<ApiResponse<String>> loadUsersFromCSVtoDatabase(@RequestBody String requestBody){
        try{
            JSONObject requestJSON = new JSONObject(requestBody);
            userService.loadUsersFromCSVFileToDatabase(requestJSON.getString("path"));
        }catch (JSONException e){
            logger.error("Invalid content of the request : " + e.getMessage());
            throw new UserBadRequestException("Invalid content of the request : " + e.getMessage());
        }
        return new ResponseEntity<>(new ApiResponse<String>("The file was successfully imported into the database " ,HttpStatus.CREATED), HttpStatus.CREATED);
    }

    /*TODO: should I create and use different class for user like UserDAO in body? */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity(new ApiResponse<String>("User created successfully",HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        if(page < 0 || size < 0){
            throw new UserBadRequestException("Page and size params cannot be negative.");
        }
        return new ResponseEntity<List<UserDto>>(userService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<String>> getUsersCount(){
        return new ResponseEntity(new ApiResponse<String>("All users in database is: " + userService.getUsersCount(), HttpStatus.OK), HttpStatus.OK) ;
    }

    @GetMapping("/oldestwithphone")
    public ResponseEntity<UserAgeDTO> getOldestUserWithPhone(){
        return new ResponseEntity(userService.getOldestUserWithPhone(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse<String>("UserId = " + userId + " has been deleted successfully", HttpStatus.OK), HttpStatus.OK) ;
    }

    /*TODO: is it not potentially dangerous for the customer/client?
    *  shouldn't I add a special mapping for this method like "/deleteAll"? */
    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deleteAllUsers(){
        userService.deleteAll();
        return new ResponseEntity(new ApiResponse<String>("All users has been deleted from database successfully", HttpStatus.OK), HttpStatus.OK) ;
    }
}
