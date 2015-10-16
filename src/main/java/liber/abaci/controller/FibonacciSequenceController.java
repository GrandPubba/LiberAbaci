package liber.abaci.controller;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Validated
@RestController
public class FibonacciSequenceController {

    public static final int MAX_N = 93;

    @RequestMapping(value = "/generate")
    public List<Long> generateFibonacciSequence(@NotNull @Min(0) @Max(MAX_N) Integer n) {
        List<Long> result = new ArrayList<Long>(n);
        for(Integer x = 0; x < n; x++) {
            if(x == 0) {
                result.add(0L);
            } else if(x == 1) {
                result.add(1L);
            } else {
                result.add(result.get(x - 2) + result.get(x - 1));
            }
        }
        return result;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
