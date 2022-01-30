package pl.majkowski.DemoRestApi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.majkowski.DemoRestApi.dto.UserDto;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserCSVFileContentException;
import pl.majkowski.DemoRestApi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Import all users from CSV file to database
    *  cases that could not be handled will be returned in exception UserCSVFileContentException
    * TODO ...to wonder if a batch save would not be better... */
    public void loadUsersFromCSVFileToDatabase(String path){
        logger.info("Loading users from CSV file to database...");
        List<String> userExceptionList = new ArrayList<>();

        //load users from .csv file
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);

        //get loaded users from .csv
        List<User> userList = userCSVFileLoader.getCSVUsers().stream()
                .map(csvUser -> new User(
                    csvUser.getFirstName(),
                    csvUser.getLastName(),
                    csvUser.getBirthDate(),
                    csvUser.getPhoneNo()))
                .collect(Collectors.toList());

        //get exceptions from .csv
        if(userCSVFileLoader.isExceptionFound()){
            userExceptionList.addAll(userCSVFileLoader.getExceptions());
        }

        // validate if PhoneNo already exist and save user to database
        // If yes -> add exception to userExceptionList
        userList.stream().forEach(user ->{
            if(user.getPhoneNo() != null && userRepository.existsByPhoneNo(user.getPhoneNo())){
                userExceptionList.add("Cant add user to database. Phone number already exist : " + user.getPhoneNo());
            }else{
                userRepository.save(user);
            }
        });

        logger.info("Users saved in database successfully");

        // throw exception if there is something
        if(!userExceptionList.isEmpty()){
            logger.warn("The file contains some exceptions that were not handled");
            throw new UserCSVFileContentException("The file contains some exceptions that were not handled",userExceptionList);
        }
    }

    /* get all users from database and map them to UserDTO */
    // TODO pagination
    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream()
                .map(user ->
                        new UserDto(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getBirthDate(),
                                user.getPhoneNo()
                        ))
                .collect(Collectors.toList());
    }

}
