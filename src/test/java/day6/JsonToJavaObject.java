package day6;
//A POJO (Plain Old java Object) class
// is used to create object that represent data
// for example :
// This is an Java object that contains 3 values for 3 fields
//Spartan sp1 = new Spartan("B20 user","Male",1234567890L) ;
//
// A POJO Class must have
// encapsulated fields
// public no arg constructor
// everything else is optional


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import pojo.Spartan;
import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import pojo.SpartanRead;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
public class JsonToJavaObject {
    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI = ConfigurationReader.getProperty("spartan.base_uri");
        basePath = "/api" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }
    @DisplayName("Get 1 Data with Save Response Json As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsMap() {
        Response response = given()
                .auth().basic("admin", "admin")
                .log().all()
                .pathParam("id", 5).
                        when()
                .get("/spartans/{id}").prettyPeek();
        // get jsonPath
        JsonPath jp = response.jsonPath();
        Map<String,Object> responseMap =  jp.getMap("") ;
        System.out.println("responseMap = " + responseMap);
        SpartanRead sp = jp.getObject("",SpartanRead.class);
        System.out.println("sp = " + sp);

        //SpartanRead sp2 = jp.get("");

        /**
         * {
         *     "id": 5,
         *     "name": "Sayeem",
         *     "gender": "Male",
         *     "phone": 1231231230
         * }
         * jsonPath to get whole json object is just empty string
         *
         * assume this is a car object
         * {
         *     "make":"Honda"
         *     "color" : "white"
         *     "engine" : {
         *                   "type" : "v8"
         *                   "horsepower" : 350
         *                }
         * }
         * jsonPath for horse power -->> engine.horsepower
         * jsonPath for engine itself -->> engine
         * jsonPath for entire car JsonObject -->> ""
         *
         *
         */


    }

    @DisplayName("Get All Data with Save Response JsonArray As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsJavaObject() {
        Response response = given()
                                    . auth().basic("admin", "admin").
                            when()
                                    .get("/spartans");

        JsonPath jp = response.jsonPath();
        List<SpartanRead> allSpartanPojos = jp.getList("", SpartanRead.class);
        //System.out.println("allSpartanPojos = " + allSpartanPojos);
        allSpartanPojos.forEach(System.out::println);
    }
}
