package day7;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
public class PatchOneSpartanTest {
    @BeforeAll
    public static void setUp() {
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI = ConfigurationReader.getProperty("spartan.base_uri");
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }
    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdate(){
        // we just want to update of one spartan
        Map<String , Object> patchBodyMap = new LinkedHashMap<>();
        patchBodyMap.put("name","seckin");
        patchBodyMap.put("phone",1234554321L);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", 100)
                .body(patchBodyMap).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .statusCode(is (204))
                ;



    }
    @DisplayName("Patching 1 data with POJO")
    @Test
    public void testPath1DataPartialUpdateWithPOJO(){
        // we just want to update of one spartan
        //Spartan sp = new Spartan("seckin","",5432112345L); it will fail
        Spartan sp = new Spartan();
        sp.setName("seckin");
        sp.setPhone(1231231234L);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", 100)
                .body(sp).
        when()
                .patch("/spartans/{id}").
                then()
                .log().all()
                .statusCode(is (500))
        ;



    }





}