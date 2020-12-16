package day5;

import Utility.ConfigurationReader;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AssertingCollectionInTheChain {


    @BeforeAll
    public static void setUp() {
        String spartan1 = "http://100.24.238.65:8000";
        String spartan2 = "http://100.26.101.158:8000";
        String spartan3 = "http://54.90.101.103:8000";// locked ip address
        baseURI = ConfigurationReader.getProperty("spartan.base_uri");
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }

    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData() {

        // search for nameContains : a , gender Female
        // verify status code is 200


        given()
                .log().all()
                .auth().basic("admin", "admin")
                .queryParam("nameContains", "a")
                .queryParam("gender", "Female").
                when()
                .get("/spartans/search").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                //.body("numberOfElements", equalTo(17))
                //.body("content",hasSize(17))
                .body("content.name", everyItem(containsStringIgnoringCase("a") ))
                .body("content.gender", everyItem(equalTo("Female")))
        ;

    }
}

