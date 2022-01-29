package pl.majkowski.DemoRestApi.service;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.majkowski.DemoRestApi.dao.CSVUser;
import pl.majkowski.DemoRestApi.exception.UserCSVFileNotFoundException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


/* class designed to read users from a .csv file to CSVUser objects
* CSV file must have columns: "first_name", "last_name", "birth_date"
* Optional column: "phone_no"
* */
public class UserCSVFileLoader {

    private static final Logger logger = LogManager.getLogger(UserCSVFileLoader.class);
    private CsvToBean<CSVUser> csvToBean;
    private List<CSVUser> csvUserList;
    private List<String> exceptionList;
    private boolean isExceptionFound = false;

    public UserCSVFileLoader(String path){
        load(path);
    }

    /* Load content of CSV file intended for User data and store it in internal variable csvUserList
    * exceptions are stored in internal variable exceptionList */
    private void load(String path){
        logger.info("Loading CSV file from path " + path);
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            csvToBean = new CsvToBeanBuilder(reader)
                    .withSeparator(';')
                    .withType(CSVUser.class)
                    .withThrowExceptions(false)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            csvUserList = csvToBean.parse();
            logger.info("CSV file loaded successful");

            if (csvToBean.getCapturedExceptions().size() > 0) {
                isExceptionFound = true;
                exceptionList = csvToBean.getCapturedExceptions().stream()
                        .map(e -> "Line " + e.getLineNumber() + ": " + e.getMessage())
                        .collect(Collectors.toList());
                logger.warn("Exceptions found in file " + path + "\n" + exceptionList);
            }

        }catch (RuntimeException e){
            logger.error("Error in file: " + e);
            throw new IllegalArgumentException(e);
        }catch (NoSuchFileException e) {
            logger.error("File not found " + e.getMessage());
            throw new UserCSVFileNotFoundException("File not found " + e.getMessage());
        }catch (IOException e){
            logger.fatal(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CSVUser> getCSVUsers(){
        return csvUserList;
    }

    public List<String> getExceptions(){
        return exceptionList;
    }

    public boolean isExceptionFound(){
        return isExceptionFound;
    }
}
