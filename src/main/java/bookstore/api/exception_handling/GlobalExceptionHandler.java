package bookstore.api.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exception){

        Map<String, String> errorsMap = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errorsMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPasswordException.class)
    public Map<String,String> handleWrongPassword(WrongPasswordException exception){

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error message", exception.getMessage());

        return errorsMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String,String> handleNotFound(NotFoundException exception){

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error message", exception.getMessage());

        return errorsMap;
    }
}
