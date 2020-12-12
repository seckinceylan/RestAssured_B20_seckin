package day4;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class OpenMovieDB_Test {

    @BeforeAll
    public static void setUp() {
        //String spartan1 = "http://100.24.238.65:8000/api/hello";
        //        String spartan2 ="http://100.26.101.158:8000/api/hello";
        // //http://www.omdbapi.com/?t=The Orville&apiKey=6fff7f5a
        baseURI = "http://www.omdbapi.com";
        //basePath = "/api";
        }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }


    @DisplayName("Test Search Movie or open Movie DB Test")
    @Test
    public void testMovie(){
        given()
                .queryParam("t","The Orville")
                .queryParam("apikey","6fff7f5a").
        when()
                .get().
                prettyPeek().
        then()
                .statusCode(is (200))
                .contentType(ContentType.JSON)
                .body("Title", is ("The Orville"))
                .body("Ratings[0].Source", is ("Internet Movie Database") )

        ;
    }

    @DisplayName("Getting the log of request amd response")
    @Test
    public void testSendingRequestAndGettheLog(){
        given()
                .queryParam("t","John Wick")
                .queryParam("apikey","6fff7f5a")
                // logging the request should be in given section
                //.log().all().
                //.log().uri()
                .log().parameters().
        when()
                .get().
                //prettyPeek().
        then()
                //// logging the response should be in given section
                //.log().all()
                .log().body()
                .log().ifValidationFails()
                .statusCode(is (200))
                .body("Plot", containsString("ex-hit") )
                .body("Ratings[1].Source", is ("Rotten Tomatoes"))

        ;




    }



}
