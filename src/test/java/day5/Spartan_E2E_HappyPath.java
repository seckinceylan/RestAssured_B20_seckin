package day5;

import Utility.ConfigurationReader;
import Utility.SpartanUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("CRUD Spartan")
public class Spartan_E2E_HappyPath {
    // crud operation create read update and delete

    private static Map<String,Object> payloadMap ;
    private static int newID;

    @BeforeAll
    public static void setUp(){
            baseURI = ConfigurationReader.getProperty("spartan.base_uri");
            basePath = "/api" ;
            payloadMap = SpartanUtil.getRandomSpartanRequestPayload();
            //newID = payloadMap.get("id",);
    }
    @AfterAll
    public static void tearDown(){
            reset();
    }

    @DisplayName("1. Testing POST /api/spartans Endpoint")
    @Test
    public void testAddData(){
        newID = given()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .log().all().
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                //assert response body is same as faker generated
                .body("data.name",is (payloadMap.get("name")))
                .body("data.gender",is (payloadMap.get("gender")))
                .body("data.phone",is (payloadMap.get("phone")))
                .extract()
                .jsonPath()
                .getInt("data.id")
                ;
        System.out.println("newID = " + newID);


    }

    @DisplayName("2. Testing GET /api/spartans/{id} Endpoint")
    @Test
    public void testGet1SpartanData(){
        given()
                .auth().basic("admin","admin")
                .pathParam("id", newID)

                .log().all().
        when()
                .get("spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is (newID))
                .body("name",is (payloadMap.get("name")))
                .body("gender",is (payloadMap.get("gender")))
                .body("phone",is (payloadMap.get("phone")))

                ;

    }
    @DisplayName("3. Testing PUT /api/spartans/{id} Endpoint")
    @Test
    public void testUpdate1SpartanData(){
        // we want to have different method to update the payload
        // we can rerun the method to override
        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();
        given()
                .auth().basic("admin","admin")
                .pathParam("id", newID)
                .body(payloadMap)// updated payload
                .contentType(ContentType.JSON)
                .log().all().
        when()
                .put("spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))
                .body(emptyString())

        ;
        // in order to make sure update is done
        given()
                .auth().basic("admin","admin")
                .pathParam("id", newID)
                .contentType(ContentType.JSON)
                .log().all().
                when()
                .get("spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id",is (newID))
                .body("name",is (payloadMap.get("name")))
                .body("gender",is (payloadMap.get("gender")))
                .body("phone",is (payloadMap.get("phone")))

        ;
    }

    @DisplayName("4. Testing DELETE /api/spartans/{id} Endpoint")
    @Test
    public void testDelete1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .delete("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                .body( emptyString() ) ;

        // in order to make sure the delete actually happened
        // i want to make another get request to this ID expect 404
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is (404) ) ;

    }

}
