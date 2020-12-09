package day1;
//
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class RestAssuredIntro {
    @DisplayName("Spartan /api/Hello Endpoint Test")
    @Test
    public void TestHello(){

        String spartan1 = "http://100.24.238.65:8000/api/hello";
        String spartan2 ="http://100.26.101.158:8000/api/hello";

        Response response1 = get(spartan1);
        System.out.println("response1.statusCode() = " + response1.statusCode());
        assertThat(response1.statusCode(),is (200));
        String payload1 = response1.prettyPrint();
        assertThat(payload1, is("Hello from Sparta"));
        //get the header called contenttype from response
        System.out.println( response1.getHeader("Content-Type"));
        System.out.println( response1.getContentType());
        System.out.println( response1.contentType());
        assertThat(response1.contentType(),is("text/plain;charset=UTF-8"));
        assertThat(response1.contentType(), startsWith("text"));
        assertThat(response1.contentType(),startsWith(ContentType.TEXT.toString()));



        Response response2 = get(spartan2);
        System.out.println("response2.statusCode() = " + response2.statusCode());
        assertThat(response2.statusCode(),is (200));
        String payload2 = response2.prettyPrint();
        assertThat(payload2, is("Hello from Sparta"));
        System.out.println( response2.getHeader("Content-Type"));
        System.out.println( response2.getContentType());
        System.out.println( response2.contentType());
        assertThat(response2.contentType(),is("text/plain;charset=UTF-8"));
        assertThat(response2.contentType(), startsWith("text"));

    }
    @DisplayName("Common Matchers for String")
    @Test
    public void testString(){
        String str = "Rest Assured is cool so far";
        assertThat(str, is ("Rest Assured is cool so far"));
        assertThat(str, equalToIgnoringCase("Rest Assured is COOL so far"));
        assertThat(str, startsWith("Rest"));
        assertThat(str, containsString("is cool"));
        assertThat(str, containsStringIgnoringCase("IS COOL"));
    }

}
