package day4;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
public class SpartanUpdatingTest {
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
    @DisplayName("Testing PUT /api/spartans/{id}")
    @Test
    public void testUpdatingSingleSpartan(){
        String updateStrPayload =  "    {\n" +
                "        \"name\": \"Tayfun\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }" ;

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .body(updateStrPayload).
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))


        ;
    }

    @DisplayName("Testing PATCH /api/spartans/{id} with string body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){
        // update the name to B20 Patched
        // {"name" : "B20 Patched"}
        String patchBody = "{\"name\" : \"Seco\"}";
        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 3)
                .body(patchBody).
                when()
                .patch("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                ;


    }
    @DisplayName("Testing DELETE /api/spartans/{id} ")
    @Test
    public void testDeletingSingleSpartan(){
        given()
                .log().all()
                .auth().basic("admin", "admin")
                .pathParam("id", 75).
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
        ;



    }




}
