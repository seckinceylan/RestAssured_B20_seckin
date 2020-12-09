import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloTest {
    @Test
    public void test(){
        Assertions.assertEquals(4,1+3);
    }
    @Test
    public void test2(){
        assertEquals(12,3*4);
    }

}
