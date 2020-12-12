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

public class SpartanAddingUpdatingTest {
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
    @DisplayName("Testing Get /api/spartans with basic auth")
    @Test
    public void testAllSpartansWithBasicAuth(){
        given()
                .auth().basic("admin", "admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(is (200))
        ;

    }
    @DisplayName("Add 1 data with raw Json string Post /api/spartans")
    @Test
    public void testAddOneData(){
        String newSpartanStr =  "    {\n" +
                "        \"name\": \"Gulbadan\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }" ;

        given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(newSpartanStr).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is ("A Spartan is Born!"))
                .body("success", startsWith ("A"))
                .body("data.name", is ("Gulbadan"))
                .body("data.gender", is ("Gulbadan"))
                .body("data.phone", is (9876543210L))

                ;
    }
    @DisplayName("Add 1 data with map object Post /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){
        Map<String,Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name","Tayfun");
        payloadMap.put("gender","Male");
        payloadMap.put("phone",9879879870L);
        System.out.println("payloadMap = " + payloadMap);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(payloadMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is ("A Spartan is Born!"))
                .body("success", startsWith ("A"))
                .body("data.name", is ("Tayfun"))
                .body("data.gender", is ("Male"))
                .body("data.phone", is (9879879870L))
        ;
    }
    @DisplayName("Add 1 Data with External json file POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){
        // Create a file called singleSpartan.json right under root directory
        // with below content
    /*
    {
        "name": "Olivia",
        "gender": "Female",
        "phone": 6549873210
    }
    add below code to point File object to this singleSpartan.json
     */
        File externalJson = new File("singleSpartan.json");
        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(externalJson).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is ("A Spartan is Born!"))
                .body("success", startsWith ("A"))
                .body("data.name", is ("Olivia"))
                .body("data.gender", is ("Female"))
                .body("data.phone", is (6549873210L))
        ;

    }



}
