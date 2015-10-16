package liber.abaci.controller;

import liber.abaci.config.FibonacciSequenceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FibonacciSequenceConfig.class)
public class FibonacciSequenceControllerTest {

    @Autowired
    private FibonacciSequenceController fibonacciSequenceController;

    @Test
    public void shouldReturnValidFibonacciSequences()  {
        Map<Integer, List<Long>> testData = new HashMap<Integer, List<Long>>();
        testData.put(0,  new ArrayList<Long>());
        testData.put(1,  Arrays.asList(0L));
        testData.put(2,  Arrays.asList(0L, 1L));
        testData.put(3,  Arrays.asList(0L, 1L, 1L));
        testData.put(4,  Arrays.asList(0L, 1L, 1L, 2L));
        testData.put(5,  Arrays.asList(0L, 1L, 1L, 2L, 3L));
        testData.put(6,  Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L));
        testData.put(7,  Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L));
        testData.put(8,  Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L));
        testData.put(9,  Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L));
        testData.put(10, Arrays.asList(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L));
        for(Map.Entry<Integer, List<Long>> entry : testData.entrySet()) {
            List<Long> result = fibonacciSequenceController.generateFibonacciSequence(entry.getKey());
            assertEquals(entry.getValue(), result);
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowAValidationExceptionWhenANegativeNumberIsPassed() {
        fibonacciSequenceController.generateFibonacciSequence(-1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowAValidationExceptionWhenNullIsPassed() throws Exception {
        fibonacciSequenceController.generateFibonacciSequence(null);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldThrowAConstraintViolationExceptionWhenMAX_NIsExceeded() {
        fibonacciSequenceController.generateFibonacciSequence(FibonacciSequenceController.MAX_N + 1);
    }

    @Test
    public void sequenceShouldIncreaseInValueAsNIncreases() {
        Long previousLastResult = 0L;
        for(Integer n = 1; n < FibonacciSequenceController.MAX_N; n++) {
            List<Long> result = fibonacciSequenceController.generateFibonacciSequence(n);
            Long lastResult = result.get(result.size() - 1);
            assertTrue(lastResult + " < " + previousLastResult + " when n = " + n, lastResult >= previousLastResult);
            previousLastResult = lastResult;
        }
    }
}