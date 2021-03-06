package day1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 1 Hello Test")
public class HelloTest {
    // Junit5 Annotations
    // @BeforeAll @AfterAll @BeforeEach @AfterEach
    // @DisplayName @Disabled
    @BeforeAll
    public static void setUp(){
        System.out.println("@BeforeAll is running");
    }

    @AfterAll
    public static void tearDown(){
        System.out.println("@AfterAll is running");
    }

    @BeforeEach
    public void setUpTest(){
        System.out.println("@BeforeEach is running");
    }
    @AfterEach
    public void tearDownTest(){
        System.out.println("@AfterEach is running");
    }

    @DisplayName("test1 is about 1+3=4")
    @Test
    public void test1(){
        System.out.println("test 1 is running");
        Assertions.assertEquals(4,1+3);
    }
    @Disabled// it ignores the test and it will not run
    //@DisabledIf("")
    @DisplayName("test2 is about 3*4=12")
    @Test
    public void test2(){
        System.out.println("test 2 is running");
        // assert 4 times 3 is 12
        assertEquals(12, 4 * 3 );
    }

}
