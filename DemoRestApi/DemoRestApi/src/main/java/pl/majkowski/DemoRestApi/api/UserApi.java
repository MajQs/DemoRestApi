package pl.majkowski.DemoRestApi.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.majkowski.DemoRestApi.dto.ApiResponse;
import pl.majkowski.DemoRestApi.dto.UserDto;
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

    @PostMapping
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

}
