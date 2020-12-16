package day5;

// by default the test is running on alphabetical order
// or the test method name
import org.junit.jupiter.api.* ;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.MethodOrderer.* ;

//@TestMethodOrder(OrderAnnotation.class)// order by order annotation
//@TestMethodOrder(Random.class) //running randomly
//@TestMethodOrder(MethodName.class)// default option alphabetic order testA, testB...
@TestMethodOrder(MethodOrderer.DisplayName.class)// order by display name
public class TestOrderingInJunit5 {

    @Order(3)
    @DisplayName("3. Test")
    @Test
    public void testA(){
        System.out.println("running test A");
    }
    @Order(1)
    @DisplayName("1. Test")
    @Test
    public void testC(){
        System.out.println("running test C");
    }
    @Order(4)
    @DisplayName("2. Test")
    @Test
    public void testD(){
        System.out.println("running test D");
    }
    @Order(2)
    @DisplayName("4. Test")
    @Test
    public void testB(){
        System.out.println("running test B");
    }


}