package day9;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

public class JUnit5RepeatedTest {

    @RepeatedTest(5)// number of the repeating times
    public void testRepeating(){
        Faker faker = new Faker();
        //System.out.println("faker.funnyName().name() = " + faker.funnyName().name());
        System.out.println("faker.chuckNorris().fact() = " + faker.chuckNorris().fact());
    }

}
