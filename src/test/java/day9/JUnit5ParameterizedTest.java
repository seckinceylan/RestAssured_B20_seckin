package day9;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnit5ParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9})
    public void test1(int myNumber){
        // assert the number 5,6,7,8,9 all less that 10
        assertTrue(myNumber < 10);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv", numLinesToSkip = 1)  // column name almasin diye 1 den baslattik
    public void test2(String zip){
        System.out.println("zip = " + zip);
        given()
                .baseUri("http://api.zippopotam.us")
                .pathParam("zipcode", zip).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/country_zip.csv" , numLinesToSkip = 1)
    public void testCountryZipCodeCombo(String csvCountry, int csvZip){

            given()
                    .log().uri()
                    .baseUri("https://api.zippopotam.us")
                    .pathParam("country" , csvCountry)
                    .pathParam("zipcode" , csvZip).
                    when()
                    .get("/{country}/{zipcode}").
                    then()
                    .statusCode(200) ;

    }


}
