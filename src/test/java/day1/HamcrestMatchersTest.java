package day1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class HamcrestMatchersTest {
    // to get full power of rest assured library we will use ham crest matchers
    // hamcrest
    @DisplayName("test1 is about 1+3=4")
    @Test
    public void test1(){
        System.out.println("test 1 is running");
        assertThat(1+3, is (4));
        assertThat(1+3, equalTo(4));

        // it will show the message when it fails
        //assertThat("Wrong result", 1+3,equalTo(3));

        // test 1+3 is not 5
        assertThat(1+3, not(5));
        assertThat(1+3, is (not(5)));
        assertThat(1+3, not (equalTo(5)));
        assertThat(1+3, lessThan(5));
        assertThat(1+3, greaterThan(2));

    }
}
