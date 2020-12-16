package day5;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestCollectionSupport {
    @Test
    public void testList(){
        List<Integer> numList = Arrays.asList(4,6,7,9,5,88,90);
        assertThat(numList, hasSize(7));
        assertThat(numList, hasItem(88));
        assertThat(numList, hasItems(88,9));
        assertThat(numList, everyItem(is(greaterThan(3))));
        List<String>allNames = Arrays.asList("Roray", "Mariana","Olivia","Gulbadan","Ayxamgul","Kareem","Virginia","Tahir Zohra");
        assertThat(allNames, hasSize(8));
        assertThat(allNames, hasItems("Virginia", "Kareem"));
        assertThat(allNames, everyItem(containsString("a")));
        assertThat(allNames, everyItem(containsStringIgnoringCase("a")));
        assertThat("Murat Degirmenci", allOf(startsWith("Mu"), containsString("men")));// and logic
        assertThat("Ramazan Alic", anyOf(is("Ramazan"), endsWith("ic")));// 0r logic









    }
}
