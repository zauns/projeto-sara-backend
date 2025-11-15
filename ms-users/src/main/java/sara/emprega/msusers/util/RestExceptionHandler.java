package sara.emprega.msusers.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import  org.springframework.web.bind.annotation.ExceptionHandler;
import sara.emprega.msusers.exception.UserNotFoundException;
import sara.emprega.msusers.exception.VideoNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<RestErrorMessage> ArgNotValidHandler(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));

    }
     */

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<RestErrorMessage> IllegalArgumentHandler(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<RestErrorMessage> UserNotFoundHandler(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(VideoNotFoundException.class)
    ResponseEntity<RestErrorMessage> VideoNotFoundHandler(VideoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

}
