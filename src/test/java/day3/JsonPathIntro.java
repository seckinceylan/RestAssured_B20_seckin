package day3;
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
public class JsonPathIntro {
    @BeforeAll
    public static void setUp() {
        String spartan1 = "http://100.24.238.65:8000";
        String spartan2 ="http://100.26.101.158:8000";
        baseURI = spartan2;
        basePath = "/api";

    }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }

    @DisplayName("Extracting data out of SpartanJson Object")
    @Test
    public void test1SpartanPayload(){
        //send a request to get 1 spartan
        // by providing path params with valid Id
        // save it into Response object
        // by calling the method jsonPath() on response object
        // extract id name gender phone
        // and save it to a variable of correct type

        Response response = given()
                                .pathParam("id", 111).
                            when()
                                .get("/spartans/{id}")
                                .prettyPeek(); // returns the all

        //response.prettyPrint();// returns the body

        // jsonPath is used to navigate through the json payload
        //and extract the value according to valid "jsonpath" provided
        JsonPath jp = response.jsonPath();
        int myId = jp.getInt("id");
        String myName = jp.getString("name");
        String myGender = jp.getString("gender");
        long myPhone = jp.getLong("phone");
        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("myGender = " + myGender);
        System.out.println("myPhone = " + myPhone);


    }
    @DisplayName("Extracting data from Json Array Response")
    @Test
    public void getAllSpartanExtractData(){
        //Response response = get("/spartans");
        //JsonPath jp = response.jsonPath();
        JsonPath jp = get("/spartans").jsonPath();

        // get the first spartan
        System.out.println("jp.getString(\"name[0]\") = " + jp.getString("name[0]"));
        System.out.println("jp.getLong(\"phone[0]\") = " + jp.getLong("phone[0]"));

        // get the 7 th json object gender
        System.out.println("jp.getString(\"gender[6]\") = " + jp.getString("gender[6]"));

        List<String> allNames = jp.getList("name");
        //System.out.println("allNames = " + allNames);
        List<Long> allPhones = jp.getList("phone");
        //System.out.println("allPhones = " + allPhones);
        System.out.println("jp.getJsonObject() = " + jp.getJsonObject("id"));


    }
    // send request to this url
    //http://100.26.101.158:8000/api/spartans/search?nameContains=de&gender=Male
    @DisplayName("Testing /spartans/search and extracting data")
    @Test
    public void testSearch(){
        JsonPath jp = given()
                            .queryParam("nameContains", "de")
                            .queryParam("gender","Male").
                      when()
                            .get("/spartans/search")
                            .jsonPath()  ;
        // first guy
        System.out.println("jp.getString(content[0]) = " + jp.getString("content[0].name"));
        // first guy name
        System.out.println("jp.getString(\"content[0].name\") = " + jp.getString("content[0]"));
        // third guy phone number
        System.out.println("jp.getLong(\"content[2].phone\") = " + jp.getLong("content[2].phone"));
        // all names
        System.out.println("jp.getList(\"content.name\") = " + jp.getList("content.name"));
        // all phones
        System.out.println("jp.getList(\"content.phone\") = " + jp.getList("content.phone"));
        //
        System.out.println("jp.getBoolean(\"pageable.sort.empty\") = " + jp.getBoolean("pageable.sort.empty"));
    }



}
