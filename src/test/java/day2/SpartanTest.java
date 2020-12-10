package day2;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.get;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanTest {
    @BeforeAll
    public static void setUp(){
        baseURI =  "http://100.24.238.65:8000";
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown(){
        reset();// reset baseURI and basePath value to original value.

    }


    @DisplayName("Testing /api/spartans endpoint")
    @Test
    public void testGetAllSpartans(){
        String spartan2 ="hello";
        String spartan1 = "/spartans";
        Response response1 = get(spartan1);
        response1.prettyPrint();
        System.out.println("response1.statusCode() = " + response1.statusCode());
        assertThat(response1.statusCode(), is(200));
        System.out.println("response1.contentType() = " + response1.contentType());
        assertThat(response1.contentType(), is (ContentType.JSON.toString()));
    }

    @DisplayName("Testing /api/spartans XML response")
    @Test
    public void testGetAllSpartansXML(){
        // * given
        // *      request specification
        // *      base url base path
        // *      body, header, query param, path variable, authentication authorization
        // *      logging, cookie
        // * when
        // *      this where we send the request http method
        // *      like GET, POST, PUT PATCH DELETE with URL
        // *      we get response object after sending the request
        // * then
        // *      validatable response
        // *      assertion or verification header payload, cookie
        // *      validate the response time.
        String spartan1 = "/spartans";
        //Response response1 = get(spartan1);
        //response1.prettyPrint();
        given()
                .header("accept","application/xml").
        when()
                .get(spartan1).
        then()
                //.assertThat()
                .statusCode(200)
                //.and()
                .header("Content-Type","application/xml")
        ;

        given()
                .accept(ContentType.XML).
        when()
                .get(spartan1).
        then()
                .assertThat()
                .statusCode(is (200))
                .and()
                .contentType(ContentType.XML);
    }





}
