package com.myspring.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

// import service
import com.myspring.demo.service.CalculationService;

@DisplayName("CalculationServiceTests") // describe
public class CalculationServiceTests {

    private final CalculationService service = new CalculationService();

    @Test
    @DisplayName("Should make an addition")
    void shouldAddTwoNumbers() {

        int result = service.add(10, 20);

        assertEquals(30, result);
    }
    
}
