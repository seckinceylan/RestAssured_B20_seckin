package day8;

import TestBase.HR_ORDS_TestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import pojo.Region;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class HR_ORDS_Test extends HR_ORDS_TestBase {


    @DisplayName("Test GET  to POJO")
    @Test
    public void testCountryResponseToPOJO(){
        //Response response = get("/countries/{country_id}", "AR").prettyPeek();
        Response response = given()
                                    .pathParam("country_id", "AR").
                            when()
                                    .get("/countries/{country_id}")
                                    .prettyPeek();
        Country ar = response.as(Country.class);
        System.out.println("ar = " + ar);

        Country ar1 = response.jsonPath().getObject("",Country.class);
        System.out.println("ar1 = " + ar1);


    }

    @DisplayName("Test GET /countries to List of POJO")
    @Test
    public void testAllCountriesResponseToListOfPOJO(){
        Response response = get("/countries");
        List<Country> countryList = response.jsonPath().getList("items", Country.class);
        countryList.forEach(System.out::println);




    }

}
