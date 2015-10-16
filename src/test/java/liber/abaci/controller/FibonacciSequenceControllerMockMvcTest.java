package liber.abaci.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import liber.abaci.config.FibonacciSequenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FibonacciSequenceConfig.class)
@WebAppConfiguration
public class FibonacciSequenceControllerMockMvcTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnValidFibonacciSequences() throws Exception {
        Map<Integer, List<Integer>> testData = new HashMap<Integer, List<Integer>>();
        testData.put(0,  new ArrayList<Integer>());
        testData.put(1,  Arrays.asList(0));
        testData.put(2,  Arrays.asList(0, 1));
        testData.put(3,  Arrays.asList(0, 1, 1));
        testData.put(4,  Arrays.asList(0, 1, 1, 2));
        testData.put(5,  Arrays.asList(0, 1, 1, 2, 3));
        testData.put(6,  Arrays.asList(0, 1, 1, 2, 3, 5));
        testData.put(7,  Arrays.asList(0, 1, 1, 2, 3, 5, 8));
        testData.put(8,  Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13));
        testData.put(9,  Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21));
        testData.put(10, Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34));

        for(Map.Entry<Integer, List<Integer>> entry : testData.entrySet()) {
            MvcResult mvcResult = mockMvc.perform(get("/generate?n={n}", entry.getKey()))
                    .andExpect(status().isOk()).andReturn();
            assertThat(mvcResult.getResponse().getContentAsString(), is(not(nullValue())));
            List result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
            assertEquals(entry.getValue(), result);
        }
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenANegativeNumberIsPassed() throws Exception {
        mockMvc.perform(get("/generate?n={n}", -1))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnBadRequestWhenAnInvalidIntegerIsPassed() throws Exception {
        mockMvc.perform(get("/generate?n=A"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenADoubleIsPassed() throws Exception {
        mockMvc.perform(get("/generate?n=1.0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenNoValueIsPassed() throws Exception {
        mockMvc.perform(get("/generate"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnNotFoundWhenInvalidUrlIsRequested() throws Exception {
        mockMvc.perform(get("/generator"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUnprocessableEntityWhenAPostIsPerformed() throws Exception {
        mockMvc.perform(post("/generate"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldIgnoreUnknownParameter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/generate?n=1&pi=3.14159"))
                .andExpect(status().isOk()).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(), is(not(nullValue())));
        List result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertEquals(Arrays.asList(0), result);
    }
}
