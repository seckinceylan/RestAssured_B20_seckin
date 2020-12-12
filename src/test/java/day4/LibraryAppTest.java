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
public class LibraryAppTest {
    private static String myToken ="";
    @BeforeAll
    public static void setUp() {
        String spartan1 = "http://100.24.238.65:8000";
        String spartan2 ="http://100.26.101.158:8000";
        String spartan3 ="http://54.90.101.103:8000";// locked ip address
        String libraryQA1 = "http://library1.cybertekschool.com";
        baseURI = libraryQA1;
        basePath = "/rest/v1";


    }

    @AfterAll
    public static void tearDown() {
        reset();// reset baseURI and basePath value to original value.

    }
    @DisplayName("1Testing /login Endpoint")
    @Test
    public void testLogin(){
        myToken =
        given()
                .log().all()
                // explicitly saying the body content type is x-www-urlencoded-form-data
                .contentType(ContentType.URLENC)
                .formParam("email","librarian69@library")
                .formParam("password", "KNPXrm3S" ).
        when()
                .post("/login").
        then()
                .log().all()
                .statusCode(200)
                // we can not actually validate the token since it's dynamic
                // what we can validate though -- token field exists and it's not null
                .body("token", is(  notNullValue() ) )
                .extract()
                .jsonPath()
                .getString("token")
        ;
        System.out.println("myToken = " + myToken);


    }
    @DisplayName("Testing GET /dashboard_stats Endpoint")
    @Test
    public void ztestDashboard_stats(){
        given()
                .log().all()
                .header("x-library-token",myToken).
        when()
                .get("/dashboard_stats").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                ;
    }


}
