package day5;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class ExtractPractice {

    @BeforeAll
    public static void setUp() {
        String spartan1 = "http://100.24.238.65:8000";
        String spartan2 ="http://100.26.101.158:8000";
        String spartan3 ="http://54.90.101.103:8000";// locked ip address
        baseURI = spartan3;
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }
    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){
        // search name contains a and gender Female

        JsonPath jp = given()
                        .auth().basic("admin", "admin")
                        .queryParam("nameContains", "a")
                        .queryParam("gender", "Male")
                        .log().all().
                      when()
                        .get("/spartans/search").
                      then()
                        .assertThat()
                        .statusCode(is(200))
                        .extract()
                        .jsonPath();

        List<String> allNames = jp.getList("content.name");
        System.out.println("allNames = " + allNames);
        int numOfElements = jp.getInt("numberOfElements");
        System.out.println("numOfElements = " + numOfElements);

        assertThat(numOfElements, equalTo(allNames.size()));
        assertThat(allNames, hasSize(numOfElements));

    }
}
