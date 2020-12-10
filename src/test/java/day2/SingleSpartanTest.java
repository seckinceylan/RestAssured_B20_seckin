package day2;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SingleSpartanTest {
    @BeforeAll
    public static void setUp() {
        //String spartan1 = "http://100.24.238.65:8000/api/hello";
        //        String spartan2 ="http://100.26.101.158:8000/api/hello";
        baseURI = "http://100.24.238.65:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }

    @DisplayName("Testing GET /api/spartan/{id} endpoint")
    @Test
    public void test1Spartan() {
        given()
                .accept(ContentType.JSON).
                when()
                .get("/spartans/111").
                then()
                .statusCode(is(200));
        ///////////////////////////////////////

        given()
                .accept(ContentType.JSON)
                .pathParam("id",111).
        when()
                .get("/spartans/{id}").
        then()
                .statusCode(is(200));

        //////////////////////////////////////

        given()
                .accept(ContentType.JSON)
                .pathParam("id", 111).
        when()
                .get("/spartans/{id}")
                .prettyPeek().// prints the response the payload
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);
        //////////////////////////////////////////
        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/{id}",112)
                .prettyPeek().// prints the response the payload
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is (112))
                .body("name",equalTo("Declan"))
                .body("gender",is (equalTo("Male")))
                .body("phone", is (1231231234));
        ////////////////////////////////////////////

        given()
                //.log().all().
                .log().uri().
        when()
                .get("/spartans/111").
//                .prettyPeek().
        then()
               // .log().all()
               //.log().body()
                //.log().ifValidationFails()
                .statusCode( is(200));

    }

}
