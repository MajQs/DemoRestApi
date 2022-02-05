package pl.majkowski.DemoRestApi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.majkowski.DemoRestApi.dto.UserAgeDTO;
import pl.majkowski.DemoRestApi.dto.UserDto;
import pl.majkowski.DemoRestApi.entity.User;
import pl.majkowski.DemoRestApi.exception.UserCSVFileContentException;
import pl.majkowski.DemoRestApi.exception.UserNotFoundException;
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


        userList.stream().forEach(user ->{
            try{
                // validate entries for user
                user.setFirstName(UserValidation.getFirstName(user.getFirstName()));
                user.setLastName(UserValidation.getLastName(user.getLastName()));
                user.setBirthDate(UserValidation.getBirthDate(user.getBirthDate()));
                user.setPhoneNo(UserValidation.getPhoneNo(user.getPhoneNo()));

                // validate if PhoneNo already exist and save user to database
                // If phoneNo exist -> add exception to userExceptionList
                if(user.getPhoneNo() != null && userRepository.existsByPhoneNo(user.getPhoneNo())){
                    userExceptionList.add("Cant add user to database. Phone number already exist : " + user.getPhoneNo());
                }else{
                    userRepository.save(user);
                }
            //catch exceptions for UserValidation entries
            }catch (IllegalArgumentException e){
                userExceptionList.add("Error while validating user entries: " + e.getMessage() + " for " + user.toString());
            }
        });

        logger.info("Users saved in database successfully");

        // throw exception if there is something
        if(!userExceptionList.isEmpty()){
            logger.warn("The file contains some exceptions that were not handled " + userExceptionList);
            throw new UserCSVFileContentException("The file contains some exceptions that were not handled",userExceptionList);
        }
    }


    public User addUser(User user){
        logger.info("Creating new user: " + user.toString());
        try{
            user.setFirstName(UserValidation.getFirstName(user.getFirstName()));
            user.setLastName(UserValidation.getLastName(user.getLastName()));
            user.setBirthDate(UserValidation.getBirthDate(user.getBirthDate()));
            user.setPhoneNo(UserValidation.getPhoneNo(user.getPhoneNo()));
        }catch (IllegalArgumentException e){
            logger.error("Error while validating user entries: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        userRepository.save(user);
        logger.info("User created userId=" + user.getUserId());
        return user;
    }

    /* get all users from database and map them to UserDTO
    * TODO should count page from 0 or 1 ? */
    public List<UserDto> getAllUsers(int page, int size){
        return userRepository.findAll(PageRequest.of(page,size)).stream()
                .map(user ->
                        new UserDto(
                                user.getUserId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getBirthDate(),
                                user.getPhoneNo()
                        ))
                .collect(Collectors.toList());
    }

    public UserAgeDTO getOldestUserWithPhone(){
        User user = userRepository.getOldestUserWithPhoneNo();
        return new UserAgeDTO(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                CalenderFormatter.getAge(user.getBirthDate()),
                user.getPhoneNo());
    }

    public int getUsersCount() {
        return userRepository.getUsersCount();
    }

    public void deleteUser(Long userId){
        logger.info("Deleting user with id = " + userId + " ... ");
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            logger.info("UserId = " + userId + " has been deleted successfully");
        }else{
            logger.warn("Not found user with userId = " + userId);
            throw new UserNotFoundException("Not found user with userId = " + userId);
        }
    }

    /*TODO: should I add any exceptions or verifications here?
    *  for example if database is empty throw NothingToDeleteException? */
    public void deleteAll(){
        logger.info("Deleting all users from database...");
        userRepository.deleteAll();
        logger.info("All users has been deleted from database successfully");
    }


}
