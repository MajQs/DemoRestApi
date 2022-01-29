package pl.majkowski.DemoRestApi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.majkowski.DemoRestApi.dto.UserDto;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserCSVFileContentException;
import pl.majkowski.DemoRestApi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Import all users from CSV file to database and save all in batch
    *  cases that could not be handled will be returned in exception UserCSVFileContentException */
    public void loadUsersFromCSVFileToDatabase(String path){
        logger.info("Loading users from CSV file to database...");
        UserCSVFileLoader userCSVFileLoader = new UserCSVFileLoader(path);

        List<User> userList = userCSVFileLoader.getCSVUsers().stream()
                .map(csvUser -> new User(
                    csvUser.getFirstName(),
                    csvUser.getLastName(),
                    csvUser.getBirthDate(),
                    csvUser.getPhoneNo())
                ).collect(Collectors.toList());

        userRepository.saveAll(userList);
        logger.info("users saved in database successfully");

        if(userCSVFileLoader.isExceptionFound()){
            logger.warn("The file contains some exceptions that were not handled");
            throw new UserCSVFileContentException("The file contains some exceptions that were not handled",userCSVFileLoader.getExceptions());
        }
    }

    /* get all users from database and map them to UserDTO */
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
