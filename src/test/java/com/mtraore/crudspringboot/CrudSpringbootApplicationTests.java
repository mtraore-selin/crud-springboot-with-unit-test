package com.mtraore.crudspringboot;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrudSpringbootApplicationTests {
    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> CrudSpringbootApplication.main(new String[] {}));
    }

}
